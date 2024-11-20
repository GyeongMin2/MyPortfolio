package mainController.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.MemberDAO;
import dto.member.MemberDTO;

@WebServlet(name = "searchPassword", value = "/searchPassword.do")
public class SearchPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/member/search_pw.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");

        MemberDAO memberDAO = new MemberDAO();
        try {
            MemberDTO member = memberDAO.getMemberByUserIdNamePhone(userId, name, phone);
            if (member != null) {
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("/WEB-INF/views/member/reset_password.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/views/member/search_pw_fail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            request.getSession().setAttribute("errorAlert", "회원 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
            response.sendRedirect("searchPassword.do");
        }
    }
}