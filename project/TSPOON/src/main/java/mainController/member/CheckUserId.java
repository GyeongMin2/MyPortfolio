package mainController.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import dao.MemberDAO;

@WebServlet(name = "CheckUserId", value = "/checkUserId.do")
public class CheckUserId extends HttpServlet {
    private MemberDAO memberDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        memberDAO = new MemberDAO();
        StringBuilder errorMessage = new StringBuilder();

        String userId = request.getParameter("userId");
        boolean isAvailable = false;

        if (userId != null && !userId.trim().isEmpty()) {
            try {
                isAvailable = memberDAO.isUserIdAvailable(userId);
            } catch (SQLException e) {
                errorMessage.append("아이디 중복 검사 중 오류가 발생했습니다: " + e.getMessage());
            }
        }  

        PrintWriter out = response.getWriter();
        out.print(String.format("{\"available\": %b}", isAvailable));
        out.flush();
    }
}