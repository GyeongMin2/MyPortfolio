package net.haebup.controller.common;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/teacherList.do")
public class TeacherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String subject = req.getParameter("subject");
        switch(subject){
            case "kor":
                req.setAttribute("subject", "kor");
                req.getRequestDispatcher("/WEB-INF/common/teacherInfo/teacherList.jsp").forward(req, res);
                break;
            case "eng":
                req.setAttribute("subject", "eng");
                req.getRequestDispatcher("/WEB-INF/common/teacherInfo/teacherList.jsp").forward(req, res);
                break;
            case "math":
                req.setAttribute("subject", "math");
                req.getRequestDispatcher("/WEB-INF/common/teacherInfo/teacherList.jsp").forward(req, res);
                break;
            case "sci":
                req.setAttribute("subject", "sci");
                req.getRequestDispatcher("/WEB-INF/common/teacherInfo/teacherList.jsp").forward(req, res);
                break;
            case "soc":
                req.setAttribute("subject", "soc");
                req.getRequestDispatcher("/WEB-INF/common/teacherInfo/teacherList.jsp").forward(req, res);
                break;
            default:
                req.getRequestDispatcher("/WEB-INF/common/teacherInfo/teacherList.jsp").forward(req, res);
                break;
        }
    }
}
