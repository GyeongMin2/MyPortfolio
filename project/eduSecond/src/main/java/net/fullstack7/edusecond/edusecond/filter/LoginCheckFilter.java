package net.fullstack7.edusecond.edusecond.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.util.JSFunc;

@WebFilter(urlPatterns = {"/es/*", "/product/regist", "/notice/view"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        String requestURI = httpRequest.getRequestURI();
        log.info("LoginCheckFilter - 요청 URI: {}", requestURI);
        
        // 로그인 여부 확인
        MemberLoginDTO loginMember = (MemberLoginDTO) session.getAttribute("memberInfo");
        
        if (loginMember == null) {
            // 로그인되지 않은 경우
            if (requestURI.startsWith("/es/")) {
                log.info("비로그인 사용자 /es/ 접근 시도 - 메인으로 리다이렉트");
                httpResponse.sendRedirect("/login/login");
                return;
            } else if (requestURI.contains("/product/regist") || 
                      requestURI.contains("/notice/view")) {
                log.info("비로그인 사용자 제한된 페이지 접근 시도");
                httpResponse.setContentType("text/html; charset=UTF-8");
                // JSFunc.alertBack("로그인이 필요한 서비스입니다.", httpResponse);
                JSFunc.alertLocation("로그인이 필요한 서비스입니다.", "/login/login", httpResponse);
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
}
