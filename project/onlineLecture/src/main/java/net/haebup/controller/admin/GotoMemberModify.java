package net.haebup.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.MemberDAO;
import java.util.List;
import net.haebup.dto.member.MemberTypeDTO;

@WebServlet("/admin/member/gotoMemberModify.do")
public class GotoMemberModify extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberDTO memberDto = (MemberDTO) request.getSession().getAttribute("user");
        if(memberDto != null && memberDto.getUserType().equals("A")) {
            String userId = request.getParameter("userId");
            MemberDAO memberDAO = new MemberDAO();
            
            try {
                MemberDTO member = memberDAO.getUserInfo(userId);
                List<MemberTypeDTO> memberTypes = memberDAO.getUserType();
            
            request.setAttribute("member", member);
                request.setAttribute("memberTypes", memberTypes);
                request.getRequestDispatcher("/WEB-INF/admin/member/modifyMember.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/goto.do?page=admin/login");
        }
    }
}