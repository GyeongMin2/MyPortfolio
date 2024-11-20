package net.haebup.controller.common.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.member.MemberDAO;
import net.haebup.dto.member.MemberDTO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/member/teacher/login.do")
public class TeacherLogin extends HttpServlet {
    	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		// String userType = req.getParameter("userType");
		MemberDTO user = null;
		System.out.println(userId);
		System.out.println(userPwd);
		// System.out.println(userType);
		try {
			user = new MemberDAO().loginTeacher(userId);
			if (user != null && user.getUserPwd().equals(userPwd)) {
				System.out.println("로그인 성공");
				req.getSession().setAttribute("user", user);
				// req.getRequestDispatcher("main.do").forward(req, res);
				res.sendRedirect(req.getContextPath()+"/main.do");
			} else {
				System.out.println("로그인 실패");
				req.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
				req.getRequestDispatcher("/goto.do?page=login").forward(req, res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
