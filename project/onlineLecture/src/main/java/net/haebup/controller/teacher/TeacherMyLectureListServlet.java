package net.haebup.controller.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.haebup.dao.lecture.LectureDAO;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.member.MemberDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacherMyLectureList.do")
public class TeacherMyLectureListServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        
        if (user == null || !"T".equals(user.getUserType())) {
            response.sendRedirect("/main.do");
            return;
        }

        try {
            LectureDAO lectureDAO = new LectureDAO();
            // 선생님 ID로 강의 목록 조회 (전체 목록)
            List<LectureDTO> lectures = lectureDAO.getLecturesByTeacherId(user.getUserId());
            request.setAttribute("lectures", lectures);
            
            request.getRequestDispatcher("/WEB-INF/teacher/lecture/myLectureList.jsp")
                   .forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
