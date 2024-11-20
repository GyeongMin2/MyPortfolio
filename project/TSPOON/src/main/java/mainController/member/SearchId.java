package mainController.member;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.MemberDAO;
import dto.member.MemberDTO;

@WebServlet("/searchId.do")
public class SearchId extends HttpServlet {
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/member/search_id.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {
            session.setAttribute("errorAlert", "이름과 전화번호를 모두 입력해주세요.");
            response.sendRedirect("searchId.do");
            return;
        }

        // 전화번호 유효성 검사
        if (!phone.matches("^01[016789]-?[^0][0-9]{2,3}-?[0-9]{3,4}$")) {
            session.setAttribute("errorAlert", "올바른 휴대폰 번호 형식이 아닙니다.");
            response.sendRedirect("searchId.do");
            return;
        }

        // 하이픈 제거
        phone = phone.replaceAll("-", "");

        try {
            List<MemberDTO> members = memberDAO.getMembersByNameAndPhone(name, phone);
            request.setAttribute("foundMembers", members);
            request.getRequestDispatcher("/WEB-INF/views/member/search_id_result.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("errorAlert", "회원 ID 조회 중 오류가 발생했습니다: " + e.getMessage());
            response.sendRedirect("searchId.do");
        }
    }
}