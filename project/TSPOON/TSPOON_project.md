## 주요 소스코드

### PasswordUtil.java

비밀번호 해싱과 검증을 위한 유틸리티 클래스입니다.

```java
public class PasswordUtil {

    private static final int SALT_LENGTH = 16;
    private static final String HASH_ALGORITHM = "SHA-256";

    // 솔트 생성
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 비밀번호 해싱
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    // 비밀번호 검증
    public static boolean verifyPassword(String inputPassword, String storedPassword, String salt) {
        String hashedInputPassword = hashPassword(inputPassword, salt);
        return hashedInputPassword.equals(storedPassword);
    }

    // 새 비밀번호 생성 (해시된 비밀번호와 솔트 반환)
    public static String[] createNewPassword(String password) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return new String[]{hashedPassword, salt};
    }
}
```
#### 주요 기능
- generateSalt(): 무작위 솔트 생성
- hashPassword(): 비밀번호와 솔트를 사용하여 해시 생성
- verifyPassword(): 입력된 비밀번호 검증
- createNewPassword(): 새로운 비밀번호에 대한 해시와 솔트 생성

### LoginCheckFilter.java

로그인 상태 확인 및 자동 로그인 처리를 위한 필터 클래스입니다.

```java
@WebFilter("/*")
public class LoginCheckFilter implements Filter {
    private MemberDAO memberDAO;
    private AutoLoginDAO autoLoginDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        memberDAO = new MemberDAO();
        autoLoginDAO = new AutoLoginDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = checkLoginStatus(session,
                httpRequest,
                httpResponse,
                profileDAO);
        request.setAttribute("isLoggedIn", isLoggedIn);
        chain.doFilter(request, response);
    }

    private boolean checkLoginStatus(HttpSession session,
            HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse,
            ProfileDAO profileDAO) {
        // 이미 로그인된 경우
        if (session != null && session.getAttribute("user") != null) {
            return true;
        }

        // 쿠키가 없는 경우
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) {
            return false;
        }

        // 자동 로그인 쿠키 찾기
        Cookie autoLoginCookie = findAutoLoginCookie(cookies);
        if (autoLoginCookie == null) {
            return false;
        }

        return processAutoLogin(autoLoginCookie, httpRequest, httpResponse, profileDAO);
    }

    private Cookie findAutoLoginCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "autoLoginToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    private boolean processAutoLogin(Cookie autoLoginCookie, HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse, ProfileDAO profileDAO) {
        String token = autoLoginCookie.getValue();
        try {
            String userId = autoLoginDAO.getUserIdByToken(token);
            if (userId == null) {
                return false;
            }

            MemberDTO member = memberDAO.getMemberByUserId(userId);
            if (member == null) {
                return false;
            }

            ProfilePictureDTO profile = profileDAO.getProfilePicture(userId);
            
            // 세션 생성 및 사용자 정보 저장
            HttpSession newSession = httpRequest.getSession(true);
            newSession.setAttribute("user", member);
            newSession.setAttribute("profilePath", profile.getFilePath());
            
            // 토큰 갱신
            autoLoginDAO.refreshToken(token);
            return true;

        } catch (Exception e) {
            deleteAutoLoginCookie(httpResponse);
            return false;
        }
    }

    private void deleteAutoLoginCookie(HttpServletResponse httpResponse) {
        Cookie deleteCookie = new Cookie("autoLoginToken", "");
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        httpResponse.addCookie(deleteCookie);
    }
}
```

#### 주요 기능
- checkLoginStatus(): 모든 요청에 대해 로그인 상태를 확인
- findAutoLoginCookie(): 자동 로그인 쿠키 찾기
- processAutoLogin(): 유효한 자동 로그인 토큰이 있는 경우 자동 로그인 처리
- deleteAutoLoginCookie(): 만료된 토큰 처리
- 로그인 상태를 request에 attribute로 설정

### DbQueryUtil.java

```java
public class DbQueryUtil implements AutoCloseable {

    private PreparedStatement pstmt;
    private ResultSet rs;

    // 파라미터 있는 쿼리 문자열 배열 파라미터 받음
    public DbQueryUtil(Connection conn, String sql, String[] parameters) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);

        // 파라미터 binding
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setString(i + 1, parameters[i]);
            }
        }
    }

    // 파라미터 있는 쿼리 Object 배열 파라미터 받음 (String || Integer)
    //파일 업로드 시 파일 사이즈 받아오기 위해 Long 타입 추가
    //null 처리 추가를 위해 다른파라미터들을 setObject로 변경후 setNull 추가 :(
    public DbQueryUtil(Connection conn, String sql, Object[] parameters) throws SQLException {
        this.pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] != null) {
                    pstmt.setObject(i + 1, parameters[i]);
                } else {
                    pstmt.setNull(i + 1, java.sql.Types.NULL);
                }
            }
        }
    }

    // 페이징용 또는 정수 필요하면  정수 배열 파라미터 받음
    public DbQueryUtil(Connection conn, String sql, int[] parameters) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);

        // 파라미터 binding
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setInt(i + 1, parameters[i]);
            }
        }
    }

    public DbQueryUtil(Connection conn, String sql) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);
    }

    public ResultSet executeQuery() throws SQLException {
        this.rs = pstmt.executeQuery();
        return rs;
    }

    public int executeUpdate() throws SQLException {
        return pstmt.executeUpdate();
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return pstmt.getGeneratedKeys();
    }


    @Override
    public void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
    }
}
```
#### 주요 기능
- 쿼리 실행 및 결과 반환
- 파라미터 바인딩
- 리소스 자동 닫기

### Class Diagram & ERD
![classDiagram](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/TSPOON/TSPOON_classdiagram.png)

![erd](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/TSPOON/TSPOON_ERD.png)
