package mainController.admin;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "AdminUrlController", value = "/adminUrl")
public class AdminUrlController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.sendRedirect("/WEB-INF/main/index.jsp");
        String pageName = request.getParameter("pageName");
        request.getRequestDispatcher("WEB-INF/views/"+pageName).forward(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Handle POST request
    }

    @Override
    public void destroy() {
    }
}
