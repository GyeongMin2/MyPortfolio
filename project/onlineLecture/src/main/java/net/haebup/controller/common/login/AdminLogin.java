package net.haebup.controller.common.login;

import net.haebup.dao.member.MemberDAO;
import net.haebup.dto.member.MemberDTO;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/member/admin/login.do")
public class AdminLogin extends HttpServlet {

    // public AdminLogin() {
    //     System.out.println("AdminLogin 서블릿 생성됨");
    // }

    // @Override
    // public void init() throws ServletException {
    //     System.out.println("AdminLogin 서블릿 초기화됨");
    // }


    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String userId = req.getParameter("userId");
    String userPwd = req.getParameter("userPwd");
    // String userType = req.getParameter("userType");
    MemberDTO user = null;
    System.out.println(userId);
    System.out.println(userPwd);
    // System.out.println(userType);
    try {
        user = new MemberDAO().loginAdmin(userId);
        if (user != null && user.getUserPwd().equals(userPwd)) {
            System.out.println("로그인 성공");
            req.getSession().setAttribute("user", user);
            res.sendRedirect(req.getContextPath()+"/admin/member/memberList.do");
        } else {
            System.out.println("로그인 실패");
            req.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            // req.getRequestDispatcher("/goto.do?page=admin/adminLogin").forward(req, res);
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert('아이디 또는 비밀번호가 일치하지 않습니다.'); window.location.href='/goto.do?page=admin/login';</script>");
            out.flush();
            // res.sendRedirect(req.getContextPath()+"/goto.do?page=admin/adminLogin");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}