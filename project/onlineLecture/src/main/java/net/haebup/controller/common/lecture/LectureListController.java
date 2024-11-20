package net.haebup.controller.common.lecture;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.haebup.dao.lecture.LectureDAO;
import net.haebup.dto.lecture.LectureDTO;

@WebServlet("/lecture/common/lectureList.do")
public class LectureListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LectureDAO lectureDAO = new LectureDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        String filterBy = request.getParameter("filterBy");
        String filterValue = request.getParameter("filterValue");
        int page = 1;
        int limit = 10;

        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            // 기본값 1 사용
        }

        int offset = (page - 1) * limit;

        try {
            List<LectureDTO> lectures = lectureDAO.getLectureList(sortBy, filterBy, filterValue, limit, offset);
            int totalLectures = lectureDAO.getLectureTotalCount(filterBy, filterValue);
            int totalPages = (int) Math.ceil((double) totalLectures / limit);

            request.setAttribute("lectures", lectures);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("filterBy", filterBy);
            request.setAttribute("filterValue", filterValue);
            //<a href="?category=${lectures.lectureCode}"/>

            request.getRequestDispatcher("/WEB-INF/common/lecture/list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
