import java.util.Scanner;

public class LoginPage {
    Scanner sc = new Scanner(System.in);
    public void LoginForm(Member member){
        while (true){
            System.out.println("아이디 입력해주세여");
            String id = sc.next();
            System.out.println("비번 입력해주세여");
            String pwd = sc.next();
            Login lg = new Login(id, pwd);
            if (lg.login(member)) {
                System.out.println("로그인성공인듯");
                break;
            } else {
                System.out.println("로그인실패인듯 다시시도하고싶으면 'Y' 다시 하기 싫으면 'N'");
                String action = sc.next();
                if (action.equals("Y")) {
                    continue;
                } else {
                    break;
                }
            }
        }
    }
}
