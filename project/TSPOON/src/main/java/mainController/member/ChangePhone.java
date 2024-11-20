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

@WebServlet("/changePhone.do")
public class ChangePhone extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        String newPhone = request.getParameter("phone");
        
        if (user == null || newPhone == null || newPhone.trim().isEmpty()) {
            sendJsonResponse(response, false, "입력 정보가 올바르지 않습니다.");
            return;
        }
        
        // 전화번호 유효성 검사
        if (!newPhone.matches("^01[016789]-?[^0][0-9]{2,3}-?[0-9]{3,4}$")) {
            sendJsonResponse(response, false, "올바른 휴대폰 번호 형식이 아닙니다.");
            return;
        }

        
        try {
            MemberDAO memberDAO = new MemberDAO();
            MemberDTO member = memberDAO.getMemberByUserId(user.getUserId());
            
            if (member == null) {
                sendJsonResponse(response, false, "회원 정보 확인 중 오류가 발생했습니다.");
                return;
            }

            newPhone = newPhone.replaceAll("-", "");
            // 전화번호 업데이트
            member.setPhone(newPhone);
            memberDAO.updateMember(member);
            //성공 후 세션에 업데이트
            session.setAttribute("user", member);
            sendJsonResponse(response, true, "전화번호 변경 성공");
        } catch (Exception e) {
            sendJsonResponse(response, false, "전화번호 변경 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    private void sendJsonResponse(HttpServletResponse response, boolean success, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
    }
}