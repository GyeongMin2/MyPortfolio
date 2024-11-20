package net.haebup.controller.common.lecture;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.lecture.LectureDAO;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.lecture.lectureDetail.LectureDetailDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/lecture/common/lectureDetail.do")
public class LectureDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lectureCode = request.getParameter("lectureCode");
        LectureDAO lectureDAO = new LectureDAO();

        try {
            // 강의 기본 정보 조회
            LectureDTO lecture = lectureDAO.getLectureDetail(lectureCode);
            // 강의 상세 내용 목록 조회
            List<LectureDetailDTO> lectureDetails = lectureDAO.getLectureDetails(lectureCode);

            // 요청 속성 설정
            request.setAttribute("lecture", lecture);
            request.setAttribute("lectureDetails", lectureDetails);

            // JSP 페이지로 포워딩
            request.getRequestDispatcher("/WEB-INF/common/lecture/lectureDetail.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"강의 상세 정보를 불러오는 중 오류가 발생했습니다.");
        }
    }
}
