package net.haebup.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.haebup.dto.member.MemberDTO;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminAuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // 세션에서 사용자 정보 가져오기
        MemberDTO user = (session != null) ? (MemberDTO) session.getAttribute("user") : null;
        
        // 관리자 권한 체크
        if (user == null || !"A".equals(user.getUserType())) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/main.do");
            return;
        }
        
        // 권한이 있다면 다음 필터 또는 서블릿으로 진행
        chain.doFilter(request, response);
    }
}
