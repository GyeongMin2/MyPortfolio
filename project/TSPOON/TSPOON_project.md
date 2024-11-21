## 주요 소스코드

## 환경 설정
- **JDK 버전** : 17이상
- **Tomcat 버전** : 10.1.31

## 주요 의존성 및 버전
- **Jakarta Servlet API**: 6.1.0
- **JSTL**: 2.0.0

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

### CheckDuplicateId.java

중복 아이디 체크를 위한 유틸리티 클래스입니다.

백엔드 코드
```java
@WebServlet(name = "CheckUserId", value = "/checkUserId.do")
public class CheckUserId extends HttpServlet {
    private MemberDAO memberDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        memberDAO = new MemberDAO();
        StringBuilder errorMessage = new StringBuilder();

        String userId = request.getParameter("userId");
        boolean isAvailable = false;

        if (userId != null && !userId.trim().isEmpty()) {
            try {
                isAvailable = memberDAO.isUserIdAvailable(userId);
            } catch (SQLException e) {
                errorMessage.append("아이디 중복 검사 중 오류가 발생했습니다: " + e.getMessage());
            }
        }  

        PrintWriter out = response.getWriter();
        out.print(String.format("{\"available\": %b}", isAvailable));
        out.flush();
    }
}
```

프론트엔드 코드 
```html
<div class="formBox">
    <input type="text" id="userId" name="userId" placeholder="아이디" />
    <label for="userId">아이디</label>
    <button type="button" id="checkUserId">중복 확인</button>
    <span id="userIdError">아이디를 입력해주세요.</span>
</div>
```

```javascript
checkUserIdButton.addEventListener('click', function () {
    const userId = userIdInput.value.trim();
    fetch('checkUserId.do?userId=' + encodeURIComponent(userId))
        .then(response => response.json())
        .then(data => {
            if (data.available) {
                userIdError.textContent = '사용 가능한 아이디입니다.';
                userIdError.style.color = 'blue';
                isUserIdAvailable = true;
            } else {
                userIdError.textContent = '이미 사용 중인 아이디입니다.';
                userIdError.style.color = 'red';
                isUserIdAvailable = false;
            }
            isUserIdChecked = true;
        })
        .catch(error => {
            console.error('Error:', error);
            userIdError.textContent = '아이디 중복 확인 중 오류가 발생했습니다.';
            userIdError.style.color = 'red';
            isUserIdAvailable = false;
            isUserIdChecked = false;
        });
});
```
#### 주요 기능
- ajax를 통한 아이디 중복 검사
- 결과를 JSON 형식으로 반환


### Class Diagram & ERD
![classDiagram](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/TSPOON/TSPOON_classdiagram.png)

![erd](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/TSPOON/TSPOON_ERD.png)
