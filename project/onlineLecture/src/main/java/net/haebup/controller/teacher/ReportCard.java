package net.haebup.controller.teacher;

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

@WebServlet("/teacher/lecture/reportCard.do")
public class ReportCard extends HttpServlet {
    
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
        String userId = request.getParameter("userId");
        
        try {
            ReportCardDAO reportCardDAO = new ReportCardDAO();
            List<ReportCardDTO> reportCards = reportCardDAO.getReportCards(lectureCode, userId);
            
            request.setAttribute("reportCards", reportCards);
            request.setAttribute("lectureCode", lectureCode);
            request.setAttribute("userId", userId);
            
            request.getRequestDispatcher("/WEB-INF/teacher/lecture/reportCard.jsp")
                   .forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String lectureCode = request.getParameter("lectureCode");
        String userId = request.getParameter("userId");
        
        ReportCardDAO reportCardDAO = new ReportCardDAO();
        ReportCardDTO reportCard = new ReportCardDTO();
        
        try {
            switch (action) {
                case "add":
                    reportCard.setLectureCode(lectureCode);
                    reportCard.setUserId(userId);
                    reportCard.setReportCardName(request.getParameter("reportCardName"));
                    reportCard.setScore(request.getParameter("score"));
                    reportCardDAO.insertReportCard(reportCard);
                    break;
                    
                case "update":
                    reportCard.setReportCardIdx(Integer.parseInt(request.getParameter("reportCardIdx")));
                    reportCard.setReportCardName(request.getParameter("reportCardName"));
                    reportCard.setScore(request.getParameter("score"));
                    reportCardDAO.updateReportCard(reportCard);
                    break;
                    
                case "delete":
                    int reportCardIdx = Integer.parseInt(request.getParameter("reportCardIdx"));
                    reportCardDAO.deleteReportCard(reportCardIdx);
                    break;
            }
            
            response.sendRedirect("/teacher/lecture/reportCard.do?lectureCode=" + lectureCode + "&userId=" + userId);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
