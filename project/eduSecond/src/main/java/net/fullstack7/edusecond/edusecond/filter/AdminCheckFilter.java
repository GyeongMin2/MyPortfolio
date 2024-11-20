package net.fullstack7.edusecond.edusecond.filter;

import lombok.extern.log4j.Log4j2;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
@Log4j2
public class AdminCheckFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        String requestURI = req.getRequestURI();
        
        if (requestURI.equals("/admin/login")) {
            chain.doFilter(request, response);
            return;
        }
        
        String adminId = (String) session.getAttribute("adminId");
        
        if (adminId == null) {
            resp.sendRedirect("/admin/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
