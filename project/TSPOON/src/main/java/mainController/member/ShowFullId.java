package mainController.member;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.MemberDAO;
import dto.member.MemberDTO;

@WebServlet("/showFullId.do")
public class ShowFullId extends HttpServlet {
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedUserId = request.getParameter("selectedUserId");
        
        if (selectedUserId == null || selectedUserId.trim().isEmpty()) {
            request.setAttribute("errorAlert", "선택된 아이디가 없습니다.");
            request.getRequestDispatcher("/WEB-INF/views/member/search_id_result.jsp").forward(request, response);
            return;
        }
        
        try {
            MemberDTO member = memberDAO.getMemberByUserId(selectedUserId);
            if (member != null) {
                request.setAttribute("fullMember", member);
                request.getRequestDispatcher("/WEB-INF/views/member/search_id_full.jsp").forward(request, response);
            } else {
                request.setAttribute("errorAlert", "해당 아이디의 회원 정보를 찾을 수 없습니다.");
                request.getRequestDispatcher("/WEB-INF/views/member/search_id_result.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorAlert", "회원 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/member/search_id_result.jsp").forward(request, response);
        }
    }
}