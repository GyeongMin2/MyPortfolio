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

@WebServlet("/admin/member/memberModify.do")
public class MemberModify extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String userId = request.getParameter("userId");
        String userNickname = request.getParameter("userNickname");
        String userEmail = request.getParameter("userEmail");
        String userPhone = request.getParameter("userPhone");
        String userType = request.getParameter("userType");
        int mileage = Integer.parseInt(request.getParameter("mileage"));

        System.out.println(userId);
        System.out.println(userNickname);
        System.out.println(userEmail);
        System.out.println(userPhone);
        System.out.println(userType);
        System.out.println(mileage);
        
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId(userId);
        memberDTO.setUserNickname(userNickname);
        memberDTO.setUserEmail(userEmail);
        memberDTO.setUserPhone(userPhone);
        memberDTO.setUserType(userType);
        memberDTO.setMileage(mileage);
        
        MemberDAO memberDAO = new MemberDAO();
        
        try {
            int result = memberDAO.updateUserInfoAdmin(memberDTO);
            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/member/memberList.do");
            } else {
                request.setAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
                request.getRequestDispatcher("/WEB-INF/admin/member/modifyMember.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
        }
    }
}