package mainController.member;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import dao.MemberDAO;
import dao.AutoLoginDAO;
import dto.member.MemberDTO;

@WebServlet(name = "DeleteMember", value = "/deleteMember.do")
public class DeleteMember extends HttpServlet {

    private MemberDAO memberDAO;
    private AutoLoginDAO autoLoginDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        memberDAO = new MemberDAO();
        autoLoginDAO = new AutoLoginDAO();
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("errorAlert", "유효하지 않은 로그인 상태입니다.");
            response.sendRedirect("login.do");
            return;
        }

        String userId = user.getUserId();

        try {
            // 회원 정보 삭제
            memberDAO.deleteMember(userId);
            
            // 자동 로그인 토큰 삭제
            autoLoginDAO.deleteTokenByUserId(userId);

            session.invalidate();
            
            session = request.getSession(); // 새로운 세션 생성
            session.setAttribute("successAlert", "회원 탈퇴가 완료되었습니다.");
            response.sendRedirect("index.do");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorAlert", "회원 탈퇴 처리 중 오류가 발생했습니다.");
            response.sendRedirect("editMember.do");
        }
    }
}
