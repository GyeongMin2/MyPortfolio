import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Member member = new Member();
        PageUi pageui = new PageUi(member);
        ShowPost showPost = new ShowPost();
        WritePost writePost = new WritePost();

        pageui.firstLoadUi();
        while (true) {

            if(member.loginStatus){ //로그인 됐으면 afterLoginUi 호출
                pageui.afterLoginUi();
//                System.out.println(member.getUserId());
//                System.out.println(member.getUserName());
//                System.out.println(member.loginStatus);
            }else {
                pageui.mainUi(); //비로그인시 그냥 mainUi 호출
//                System.out.println(member.loginStatus);
//                System.out.println(member.getUserId());
//                System.out.println(member.getUserName());
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