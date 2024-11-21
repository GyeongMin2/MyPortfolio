## Java Simple Board

### Java Simple Board Project 소개
>이 프로젝트는 Java로 구현된 콘솔 기반의 간단한 게시판 시스템입니다. JDBC를 사용하여 MySQL 데이터베이스와 연동되어 있으며, 기본적인 CRUD 기능을 제공합니다.

### 주요 기능
- 회원 관리 (로그인/회원가입/로그아웃)
- 게시글 CRUD 기능

### 데이터베이스 테이블 스키마

```sql
CREATE TABLE tbl_user_info (
    user_id VARCHAR(50) PRIMARY KEY,
    user_pwd VARCHAR(256),
    user_name VARCHAR(50)
);

CREATE TABLE tbl_post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    post_title VARCHAR(100),
    post_text TEXT,
    FOREIGN KEY (user_id) REFERENCES tbl_user_info(user_id)
);
```
### 주요 소스코드

#### Main.java
- 프로그램의 진입점
- 사용자의 입력을 처리하고 전체 흐름을 제어
```java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Member member = new Member();
        PageUi pageui = new PageUi(member);
        ShowPost showPost = new ShowPost();
        WritePost writePost = new WritePost();

        pageui.firstLoadUi();
        while (true) {

            if(member.loginStatus){
                pageui.afterLoginUi();
            }else {
                pageui.mainUi();
            }
            String action = sc.next();
            if (action.equals("로그인")||action.equals("login")) {
                if (!member.loginStatus){
                    LoginPage loginPage = new LoginPage();
                    loginPage.LoginForm(member);
                }else if (member.loginStatus){
                    System.out.println("이미 로그인됨");
                }

            } else if (action.equals("회원가입")||action.equals("register")) {
                if(!member.loginStatus){
                    RegisterPage regPage = new RegisterPage();
                    regPage.registerForm();
                }else if(member.loginStatus){
                    System.out.println("이미 로그인됨 로그아웃하고 회원가입하샘");
                }

            }else if (action.equals("게시물 목록")||action.equals("postlist")) {
                //게시글 조회 메서드 추가해야함
            }
            else if (action.equals("글쓰기")||action.equals("write")) {
                if (member.loginStatus){
                //글쓰기 메서드 추가해야함
                    writePost.write(member.getUserId());
                }else {
                    System.out.println("로그인하고 글쓰기 가능");
                }

            }else if(action.equals("로그아웃")||action.equals("logout")){
                if (member.loginStatus){
                    member.logout();
                    System.out.println("로그아웃 되었습니다.");
                }else if(!member.loginStatus){
                    System.out.println("로그인을 해야 로그아웃을 할듯");
                }
            }
            else if (action.equals("종료")||action.equals("exit")) {
                System.out.println("Bye");
                break;
            }else if (action.equals("도움말")||action.equals("help")) {
                pageui.helpUi();
            } else {
                System.out.println("똑바로 입력하샘");

            }
        }
    }
}
```
#### DbConnection.java
- MySQL 데이터베이스와의 연결을 관리하는 클래스
- 싱글톤 패턴 적용
```java
public class DbConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbcTest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("db연결안됨 ㅅㄱ");
            e.printStackTrace();
        }
        return connection;
    }
}
```
#### Member.java
- 회원 정보를 관리하는 클래스
- 로그인 상태, 사용자 ID, 사용자 이름 등의 정보를 저장
```java
public class Member{
    private String userId;
    private String userName;
    boolean loginStatus = false;
    Member(){};

    public void updateStatus(){
        System.out.println(userId);
        System.out.println(userName);
        loginStatus = true;
    }
    public void logout(){
        this.loginStatus = false;
        this.userId = null;
        this.userName = null;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
```
### 향후 개선 및 추가 할 기능
- 게시글 수정 기능 추가
- 게시글 삭제 기능 추가
- 게시글 검색 기능 추가
- 게시글 페이징 처리 기능 추가
- 예외처리 강화