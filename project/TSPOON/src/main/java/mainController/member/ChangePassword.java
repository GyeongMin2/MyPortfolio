package mainController.member;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.MemberDAO;
import dto.member.MemberDTO;
import util.PasswordUtil;

@WebServlet("/changePassword.do")
public class ChangePassword extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        String newPassword = request.getParameter("password");
        String resetUserId = request.getParameter("resetUserId");
        
        // System.out.println("newPassword: " + newPassword);
        // System.out.println("resetUserId: " + resetUserId);

        // System.out.println("user : " + user);
        // System.out.println("newPassword : " + newPassword);

        // 비밀번호 재설정 모드인지 확인 
        boolean isResetMode = resetUserId != null && !resetUserId.trim().isEmpty();
        // System.out.println("비밀번호 재설정 모드 : " + isResetMode);

        if ((user == null && !isResetMode) || newPassword == null || newPassword.trim().isEmpty()) {
            // System.out.println("벨리데이션 오류");
            sendJsonResponse(response, false, "입력 정보가 올바르지 않습니다.");
            return;
        }
        // System.out.println("벨리데이션 완료");

        // 비밀번호 유효성 검사
        if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{10,16}$")) {
            sendJsonResponse(response, false, "비밀번호는 영문, 숫자, 특수문자를 포함한 10~16자리여야 합니다.");
            return;
        }

        try {
            MemberDAO memberDAO = new MemberDAO();
            MemberDTO member;

            if (isResetMode) {
                member = memberDAO.getMemberByUserId(resetUserId);
            } else {
                member = memberDAO.getMemberByUserId(user.getUserId());
            }

            if (member == null) {
                sendJsonResponse(response, false, "회원 정보 확인 중 오류가 발생했습니다.");
                return;
            }

            // System.out.println("회원 정보 확인 완료, 암호화 시작");

            // 새 비밀번호 암호화
            String[] passwordData = PasswordUtil.createNewPassword(newPassword);
            String hashedPassword = passwordData[0];
            String salt = passwordData[1];
            
            // System.out.println("암호화 완료");

            // 회원 정보 업데이트
            member.setPassword(hashedPassword);
            member.setSalt(salt);
            memberDAO.updateMember(member);

            sendJsonResponse(response, true, "비밀번호 변경 성공");
        } catch (Exception e) {
            sendJsonResponse(response, false, "비밀번호 변경 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 응답 데이터를 JSON 형식으로 변환하여 전송 성공:true 실패:false
    private void sendJsonResponse(HttpServletResponse response, boolean success, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
    }
}