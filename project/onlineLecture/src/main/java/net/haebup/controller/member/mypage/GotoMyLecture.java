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


@WebServlet("/mypage/common/gotoMyLecture.do")
public class GotoMyLecture extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberDTO memberDTO = (MemberDTO) request.getSession().getAttribute("user");
        if(memberDTO == null){
            request.setAttribute("message", "로그인 후 이용해주세요.");
            request.getRequestDispatcher("/main.do").forward(request, response);
            return;
        }
        LectureDAO lectureDAO = new LectureDAO();
        List<LectureDTO> lectureList = null;
        try {
            lectureList = lectureDAO.getLectureListByUserId(memberDTO.getUserId(), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("lectureList", lectureList);
        //<a href="?category=${lectureList.lectureCode}"/>
        request.getRequestDispatcher("/WEB-INF/common/myPage/myLecture.jsp").forward(request, response);
    }
}
