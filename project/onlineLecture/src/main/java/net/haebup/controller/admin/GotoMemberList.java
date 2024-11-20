package net.haebup.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.MemberDAO;

@WebServlet("/admin/member/memberList.do")
public class GotoMemberList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberDTO memberDto = (MemberDTO) request.getSession().getAttribute("user");
        if(memberDto != null && memberDto.getUserType().equals("A")) {
            try {
                int page = 1;
                int limit = 10; // 한 페이지에 표시할 회원 수
                if(request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
                
                String searchType = request.getParameter("searchType");
                String searchKeyword = request.getParameter("searchKeyword");
                
                MemberDAO memberDAO = new MemberDAO();
                List<MemberDTO> memberList = memberDAO.getMemberList(limit, (page-1)*limit, searchType, searchKeyword);
                int totalCount = memberDAO.getTotalMemberCount(searchType, searchKeyword);
                
                int totalPages = (int) Math.ceil((double)totalCount / limit);
                
                request.setAttribute("memberList", memberList);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("searchType", searchType);
                request.setAttribute("searchKeyword", searchKeyword);
                
                request.getRequestDispatcher("/WEB-INF/admin/member/memberList.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/goto.do?page=admin/login");
        }
    }
}
