package net.haebup.controller.member.mypage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.haebup.dao.member.MemberDAO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.lecture.LectureDTO;
import java.io.IOException;
import java.sql.SQLException;
import net.haebup.dao.lecture.LectureDAO;
import java.util.List;
import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dto.member.payment.PaymentDTO;


@WebServlet("/mypage/common/gotoMypage.do")
public class Mypage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Mypage 서블릿 시작");
        
        MemberDTO memberDTO = (MemberDTO) request.getSession().getAttribute("user");
        System.out.println("세션에서 가져온 사용자: " + (memberDTO != null ? memberDTO.getUserId() : "null"));

        if(memberDTO == null){
            System.out.println("사용자가 로그인하지 않음, 로그인 페이지로 리다이렉트");
            request.setAttribute("message", "로그인 후 이용해주세요.");
            request.getRequestDispatcher("/main.do").forward(request, response);
            System.out.println(request.getContextPath()+"/main.do");
            return;
        }

        LectureDAO lectureDAO = new LectureDAO();
        List<LectureDTO> lectureList = null;
        int lectureTotalCount = 0;
        List<PaymentDTO> paymentList = null;
        try {
            lectureList = lectureDAO.getLectureListByUserId(memberDTO.getUserId(), true);
            System.out.println("가져온 강의 목록 크기: " + (lectureList != null ? lectureList.size() : "null"));
            
            PaymentDAO paymentDAO = new PaymentDAO();
            paymentList = paymentDAO.getPaymentListAll(memberDTO.getUserId());
            // lectureTotalCount = lectureDAO.getLectureTotalCountByUserId(memberDTO.getUserId());

            System.out.println("총 강의 수: " + lectureTotalCount);


        } catch (SQLException e) {
            System.out.println("SQL 예외 발생: " + e.getMessage());
            request.setAttribute("message", "강의 목록 조회 중 오류가 발생하였습니다.");
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }

        request.setAttribute("lectureList", lectureList);
        request.setAttribute("lectureTotalCount", lectureTotalCount);
        request.setAttribute("paymentList", paymentList);
        System.out.println("myPage.jsp로 포워딩");
        request.getRequestDispatcher("/WEB-INF/common/myPage/myPage.jsp").forward(request, response);
    }
}