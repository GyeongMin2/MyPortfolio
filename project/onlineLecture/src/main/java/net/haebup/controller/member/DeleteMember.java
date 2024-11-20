package net.haebup.controller.member;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.MemberDAO;
public class DeleteMember extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberDTO memberDTO = (MemberDTO) request.getSession().getAttribute("user");
        if(memberDTO == null){
            request.setAttribute("message", "로그인 후 이용해주세요.");
            request.getRequestDispatcher("/main.do").forward(request, response);
            return;
        }
        MemberDAO memberDAO = new MemberDAO();
        try {
            memberDAO.deleteMember(memberDTO.getUserId());
            request.getSession().invalidate();
            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
