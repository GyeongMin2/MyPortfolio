package net.haebup.controller.common.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/common/login/logout.do")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.setAttribute("msg", "로그아웃 되었습니다.");
        request.setAttribute("url", "/main.do");
        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
    }
}