## 주요 소스코드

### 파일 업로드/다운로드 시스템

#### FileIOUtil.java
파일 업로드와 다운로드를 처리하는 유틸리티 클래스입니다.


```java
public class FileIOUtil {
    // 업로드 디렉토리 경로 상수
    private static final String BOARD_UPLOAD_DIR = "uploads/board";
    private static final String LECTURE_NOTICE_UPLOAD_DIR = "uploads/lecture/notice";
    private static final String LECTURE_DETAIL_UPLOAD_DIR = "uploads/lecture/detail";
    private static final int BUFFER_SIZE = 4096; // 버퍼 크기

    public static String uploadBoardAttachment(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {
        return uploadFile(request, fieldName, BOARD_UPLOAD_DIR);
    }

    public static String uploadProfilePicture(HttpServletRequest request, String fieldName)
            throws IOException, ServletException {
        return uploadFile(request, fieldName, LECTURE_NOTICE_UPLOAD_DIR);
    }

    private static String uploadFile(HttpServletRequest request, String fieldName, String subDir)
            throws IOException, ServletException {
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadPath = applicationPath + File.separator + subDir;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Part filePart = request.getPart(fieldName);
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = getSubmittedFileName(filePart);
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            String filePath = uploadPath + File.separator + uniqueFileName;
            filePart.write(filePath);
            return "/file/uploads/" + subDir + "/" + uniqueFileName;
        }
        return null;
    }
}

```

**주요 기능**:
- 게시판/강의/프로필 등 다양한 컨텍스트의 파일 업로드 처리
- UUID를 활용한 파일명 중복 방지
- 자동 디렉토리 생성 및 관리

#### FileServingController.java
웹에서 파일 확인을 처리하는 컨트롤러입니다.

```java

@WebServlet("/file/*")
public class FileServingController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        // 경로 정보가 없으면 404 에러 반환
        if (pathInfo == null || pathInfo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 경로 정보 디코딩
        pathInfo = URLDecoder.decode(pathInfo, StandardCharsets.UTF_8);
        
        // 실제 경로 계산
        String realPath = request.getServletContext().getRealPath("/uploads") 
                       + pathInfo.replace("/uploads", "");
        
        // 파일 객체 생성
        File file = new File(realPath);

        // 파일이 존재하지 않으면 404 에러 반환
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 파일 컨텐츠 유형 설정
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);

        Files.copy(file.toPath(), response.getOutputStream());
    }
} 

```
>해당 파일을 만든이유 : 실제 배포 환경에서 업로드된 파일이 이미지태그에 의해 표시되지 않는 문제를 해결하기 위해 만들었습니다.

**주요 기능**:
- 웹 서버에 업로드된 파일을 웹 브라우저에서 확인할 수 있도록 함
- 파일 컨텐츠 유형 설정


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
![classDiagram](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/onlineLecture/onlineLecture_classdiagram.png)

![erd](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/onlineLecture/onlineLecture_ERD.png)