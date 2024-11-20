package net.haebup.controller.member.mypage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dao.lecture.LectureDAO;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.lecture.lectureDetail.LectureDetailDTO;
import java.sql.SQLException;

@WebServlet("/lecture/common/watchLecture.do")
public class WatchLecture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        MemberDTO memberDTO = (MemberDTO) request.getSession().getAttribute("user");
        if(memberDTO == null) {
            response.sendRedirect("/main.do");
            return;
        }

        String lectureCode = request.getParameter("lectureCode");
        String detailIdx = request.getParameter("detailIdx");

        try {
            PaymentDAO paymentDAO = new PaymentDAO();
            LectureDAO lectureDAO = new LectureDAO();

            if (!paymentDAO.isPaid(memberDTO.getUserId(), lectureCode)) {
                response.sendRedirect("/mypage/common/gotoMyLecture.do");
                return;
            }

            paymentDAO.updateLectureStartDate(memberDTO.getUserId(), lectureCode);

            LectureDTO lecture = lectureDAO.getLectureDetail(lectureCode);
            LectureDetailDTO detail = lectureDAO.getLectureDetailByIdx(lectureCode, Integer.parseInt(detailIdx));

            request.setAttribute("lecture", lecture);
            request.setAttribute("detail", detail);
            request.getRequestDispatcher("/WEB-INF/common/myPage/watchLecture.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/mypage/common/gotoMyLecture.do");
        }
    }
}