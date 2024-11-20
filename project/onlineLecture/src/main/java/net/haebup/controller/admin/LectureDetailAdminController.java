package net.haebup.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.lecture.LectureAdminDAO;
import net.haebup.dto.lecture.lectureDetail.LectureDetailDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/lecture/detail/manage")
public class LectureDetailAdminController extends HttpServlet {
    
    private LectureAdminDAO lectureAdminDAO = new LectureAdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String lectureCode = request.getParameter("lectureCode");

        try {
            if ("list".equals(action)) {
                List<LectureDetailDTO> details = lectureAdminDAO.getLectureDetails(lectureCode);
                request.setAttribute("details", details);
                request.setAttribute("lectureCode", lectureCode);
                request.getRequestDispatcher("/WEB-INF/admin/lecture/detailList.jsp")
                      .forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "데이터베이스 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String lectureCode = request.getParameter("lectureCode");
        String message = "";
        
        try {
            if ("add".equals(action)) {
                LectureDetailDTO detail = new LectureDetailDTO();
                detail.setLectureCode(lectureCode);
                detail.setLectureDetailContent(request.getParameter("content"));
                detail.setLectureDetailFilePath(request.getParameter("filePath"));
                detail.setLectureDetailFileName(request.getParameter("fileName"));
                detail.setLectureDetailFileSize(Long.parseLong(request.getParameter("fileSize")));
                
                int result = lectureAdminDAO.insertLectureDetail(detail);
                message = (result > 0) ? "추가되었습니다." : "추가 실패했습니다.";
            } 
            else if ("update".equals(action)) {
                LectureDetailDTO detail = new LectureDetailDTO();
                detail.setLectureDetailIdx(Integer.parseInt(request.getParameter("detailIdx")));
                detail.setLectureCode(lectureCode);
                detail.setLectureDetailContent(request.getParameter("content"));
                detail.setLectureDetailFilePath(request.getParameter("filePath"));
                detail.setLectureDetailFileName(request.getParameter("fileName"));
                detail.setLectureDetailFileSize(Long.parseLong(request.getParameter("fileSize")));
                
                int result = lectureAdminDAO.updateLectureDetail(detail);
                message = (result > 0) ? "수정되었습니다." : "수정 실패했습니다.";
            } 
            else if ("delete".equals(action)) {
                int detailIdx = Integer.parseInt(request.getParameter("detailIdx"));
                int result = lectureAdminDAO.deleteLectureDetail(detailIdx, lectureCode);
                message = (result > 0) ? "삭제되었습니다." : "삭제 실패했습니다.";
            }
            
            request.setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + 
                "/admin/lecture/detail/manage?action=list&lectureCode=" + lectureCode);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                             "데이터베이스 오류가 발생했습니다.");
        }
    }
}
