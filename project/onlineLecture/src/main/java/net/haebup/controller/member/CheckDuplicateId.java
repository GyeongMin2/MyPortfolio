package net.haebup.controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.haebup.dao.member.MemberDAO;
import net.haebup.dto.member.MemberDTO;
import java.sql.SQLException;

@WebServlet("/member/checkDuplicateId.do")
public class CheckDuplicateId extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        try {
            MemberDTO existingUser = new MemberDAO().getUserInfo(userId);
            boolean isDuplicate = (existingUser != null);
            
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write("{\"isDuplicate\": " + isDuplicate + "}");
        } catch (SQLException e) {
            res.setStatus(500);
            res.getWriter().write("{\"error\": \"서버 오류가 발생했습니다.\"}");
        }
    }
}
