package net.haebup.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.lecture.LectureAdminDAO;
import net.haebup.dto.lecture.LectureDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/lecture/manage")
public class LectureAdminController extends HttpServlet {

    private LectureAdminDAO lectureAdminDAO = new LectureAdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("list".equals(action)) {
                List<LectureDTO> lectures = lectureAdminDAO.getAllLectures();
                request.setAttribute("lectures", lectures);
                request.getRequestDispatcher("/WEB-INF/admin/lecture/list.jsp").forward(request, response);
            } else if ("new".equals(action)) {
                request.getRequestDispatcher("/WEB-INF/admin/lecture/lectureAdd.jsp").forward(request, response);
            } else if ("detail".equals(action)) {
                String lectureCode = request.getParameter("lectureCode");
                LectureDTO lecture = lectureAdminDAO.getLectureDetail(lectureCode);
                request.setAttribute("lecture", lecture);
                request.getRequestDispatcher("/WEB-INF/admin/lecture/detail.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                String lectureCode = request.getParameter("lectureCode");
                LectureDTO lecture = lectureAdminDAO.getLectureDetail(lectureCode);
                request.setAttribute("lecture", lecture);
                request.getRequestDispatcher("/WEB-INF/admin/lecture/lectureModify.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("insert".equals(action)) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(request.getParameter("lectureCode"));
                lectureDTO.setLectureName(request.getParameter("lectureName"));
                lectureDTO.setLecturePrice(Integer.parseInt(request.getParameter("lecturePrice")));
                lectureDTO.setLectureLimitDate(request.getParameter("lectureLimitDate"));
                lectureDTO.setTeacherId(request.getParameter("teacherId"));
                lectureAdminDAO.insertLecture(lectureDTO);
            } else if ("update".equals(action)) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(request.getParameter("lectureCode"));
                lectureDTO.setLectureName(request.getParameter("lectureName"));
                lectureDTO.setLecturePrice(Integer.parseInt(request.getParameter("lecturePrice")));
                lectureDTO.setLectureLimitDate(request.getParameter("lectureLimitDate"));
                lectureDTO.setTeacherId(request.getParameter("teacherId"));
                lectureAdminDAO.updateLecture(lectureDTO);
            } else if ("delete".equals(action)) {
                String lectureCode = request.getParameter("lectureCode");
                lectureAdminDAO.deleteLecture(lectureCode);
            }
            response.sendRedirect(request.getContextPath() + "/admin/lecture/manage?action=list");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
        }
    }
}
