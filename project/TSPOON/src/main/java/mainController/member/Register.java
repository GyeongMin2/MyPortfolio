package mainController.member;

import java.io.*;
import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import util.PasswordUtil;
import dao.MemberDAO;
import dto.member.MemberDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.ProfileDAO;

@WebServlet(name = "Register", value = "/register.do")
public class Register extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/join.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Post 요청 들어옴");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // System.out.println("userId: " + request.getParameter("userId"));
        // System.out.println("password: " + (request.getParameter("password"));
        // System.out.println("name: " + request.getParameter("name"));
        // System.out.println("birthday: " + request.getParameter("birthday"));
        // System.out.println("gender: " + request.getParameter("gender"));
        // System.out.println("phone: " + request.getParameter("phone"));
        // System.out.println("interest: " + request.getParameter("interest"));
        // System.out.println("grade: " + request.getParameter("grade"));
        // System.out.println("locationAgreement: " + request.getParameter("locationAgreement"));
        // System.out.println("thirdpartyAgreement: " + request.getParameter("thirdpartyAgreement"));
        // System.out.println("promotionAgreement: " + request.getParameter("promotionAgreement"));
        // System.out.println("chunjaeEduAgreement: " + request.getParameter("chunjaeEduAgreement"));

        MemberDAO memberDAO = new MemberDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        // 파라미터 받기
        String userId = request.getParameter("userId");
        // String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String interest = request.getParameter("interest");
        String grade = request.getParameter("grade");
        String locationAgreement = request.getParameter("locationAgreement");
        String thirdpartyAgreement = request.getParameter("thirdpartyAgreement");
        String promotionAgreement = request.getParameter("promotionAgreement");
        String chunjaeEduAgreement = request.getParameter("chunjaeEduAgreement");

        // 유효성 검사
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        if (userId == null || userId.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("아이디를 입력해주세요.\n");
        }

        if (password == null
                || !password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{10,16}$")) {
            isValid = false;
            errorMessage.append("비밀번호는 영문, 숫자, 특수문자를 포함한 10~16자리여야 합니다.\n");
        }

        if (name == null || name.trim().length() < 2) {
            isValid = false;
            errorMessage.append("이름은 2글자 이상이어야 합니다.\n");
        }

        if (birthday == null || birthday.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("생년월일을 입력해주세요.\n");
        } else {
            LocalDate birthDate = LocalDate.parse(birthday);
            LocalDate minAgeDate = LocalDate.now().minusYears(20);
            if (birthDate.isAfter(minAgeDate)) {
                isValid = false;
                errorMessage.append("만 20세 미만은 가입할 수 없습니다.\n");
            }
        }

        if (phone == null || !phone.matches("^01[016789]-?[^0][0-9]{2,3}-?[0-9]{3,4}$")) {
            isValid = false;
            errorMessage.append("올바른 휴대폰 번호 형식이 아닙니다.\n");
        } else {
            phone = phone.replaceAll("-", "");
        }

        if (isValid) {
            try {
                // PasswordUtil을 사용하여 비밀번호 해싱 및 솔트 생성
                String[] passwordData = PasswordUtil.createNewPassword(password);
                String hashedPassword = passwordData[0];
                String salt = passwordData[1];

                MemberDTO member = new MemberDTO();
                member.setUserId(userId);
                member.setPassword(hashedPassword);
                member.setSalt(salt);
                member.setName(name);
                member.setBirthday(birthday);
                member.setGender(gender);
                // >> - 이거 없이 저장
                phone = phone.replaceAll("-", "");
                member.setPhone(phone);
                member.setInterest(interest);
                member.setGrade(grade);
                //1: 동의, 0: 동의 안함
                member.setLocationAgreement(locationAgreement != null ? 1 : 0);
                member.setThirdpartyAgreement(thirdpartyAgreement != null ? 1 : 0);
                member.setPromotionAgreement(promotionAgreement != null ? 1 : 0);
                member.setChunjaeEduAgreement(chunjaeEduAgreement != null ? 1 : 0);
                member.setMemberStatus("Y"); // 사용중인 일반 회원으로 설정 선생님인경우 T 추후변경

                // 회원 정보를 데이터베이스에 저장
                memberDAO.insertMember(member); 
                
                // 기본 프로필 사진 생성
                profileDAO.createDefaultProfile(userId);

                // 성공 페이지로 리다이렉트
                session.setAttribute("successAlert", "회원가입이 성공적으로 완료되었습니다.");
                response.sendRedirect("login.do");
            } catch (Exception e) {
                // 데이터베이스 저장 중 오류 발생
                session.setAttribute("errorAlert", "회원 가입 중 오류가 발생했습니다: " + e.getMessage());
                response.sendRedirect("register.do");
            }
        } else {
            // 유효성 검사 실패 시 에러 메시지와 함께 폼으로 되돌림
            session.setAttribute("errorAlert", errorMessage.toString());
            response.sendRedirect("register.do");
        }
    }
}
