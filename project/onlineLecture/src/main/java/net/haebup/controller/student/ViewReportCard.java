package net.haebup.controller.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.haebup.dao.lecture.ReportCardDAO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.lecture.ReportCardDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/lecture/viewReportCard.do")
public class ViewReportCard extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("/main.do");
            return;
        }

        String lectureCode = request.getParameter("lectureCode");
        String userId = user.getUserId();
        
        try {
            ReportCardDAO reportCardDAO = new ReportCardDAO();
            List<ReportCardDTO> reportCards = reportCardDAO.getReportCards(lectureCode, userId);
            
            if (!reportCards.isEmpty()) {
                request.setAttribute("lectureName", reportCards.get(0).getLectureName());
            }
            
            request.setAttribute("reportCards", reportCards);
            request.getRequestDispatcher("/WEB-INF/student/lecture/viewReportCard.jsp")
                   .forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
} 