package net.haebup.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import net.haebup.dao.member.MemberDAO;

@WebServlet("/admin/member/memberDelete.do")
public class MemberDelete extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        MemberDAO memberDAO = new MemberDAO();
        
        String jsonResponse;
        
        try {
            int result = memberDAO.deleteMember(userId);
            if (result > 0) {
                jsonResponse = "{\"success\": true}";
            } else {
                jsonResponse = "{\"success\": false}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            jsonResponse = "{\"success\": false}";
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
