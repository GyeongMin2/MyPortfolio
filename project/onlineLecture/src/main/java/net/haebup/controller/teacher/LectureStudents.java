package net.haebup.controller.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.member.payment.PaymentDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/lecture/students.do")
public class LectureStudents extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        
        if (user == null || !"T".equals(user.getUserType())) {
            response.sendRedirect("/main.do");
            return;
        }

        String lectureCode = request.getParameter("lectureCode");
        
        try {
            PaymentDAO paymentDAO = new PaymentDAO();
            List<PaymentDTO> students = paymentDAO.getStudentsByLectureCode(lectureCode);
            
            request.setAttribute("students", students);
            request.getRequestDispatcher("/WEB-INF/teacher/lecture/studentList.jsp")
                   .forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
