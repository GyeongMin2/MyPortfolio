import java.util.Scanner;
public class RegisterPage {
    Scanner sc = new Scanner(System.in);
    public void registerForm(){
        while (true){
            System.out.println("회원가입할 아이디 입력");
            String newId = sc.next();
            System.out.println("회원가입할 비번 입력");
            String newPwd = sc.next();
            System.out.println("회원가입할 이름 입력");
            String newName = sc.next();
            Register reg = new Register(newId,newPwd,newName);
            if(reg.registration()==true){
                System.out.println("회원가입성공인듯");
                continue;
            }else{
                System.out.println("회원가입 실패인듯 다시시도하고싶으면 'Y' 다시 하기 싫으면 'N'");
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
