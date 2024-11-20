package filter;

import java.io.IOException;
import java.util.Arrays;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;
import dao.MemberDAO;
import dao.AutoLoginDAO;
import dto.member.MemberDTO;
import dto.profile.ProfilePictureDTO;
import dao.ProfileDAO;

@WebFilter("/*")
public class LoginCheckFilter implements Filter {
    private MemberDAO memberDAO;
    private AutoLoginDAO autoLoginDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        memberDAO = new MemberDAO();
        autoLoginDAO = new AutoLoginDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = checkLoginStatus(session, httpRequest, httpResponse, profileDAO);
        request.setAttribute("isLoggedIn", isLoggedIn);
        chain.doFilter(request, response);
    }

    private boolean checkLoginStatus(HttpSession session, HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse, ProfileDAO profileDAO) {
        // 이미 로그인된 경우
        if (session != null && session.getAttribute("user") != null) {
            return true;
        }

        // 쿠키가 없는 경우
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null) {
            return false;
        }

        // 자동 로그인 쿠키 찾기
        Cookie autoLoginCookie = findAutoLoginCookie(cookies);
        if (autoLoginCookie == null) {
            return false;
        }

        return processAutoLogin(autoLoginCookie, httpRequest, httpResponse, profileDAO);
    }

    private Cookie findAutoLoginCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "autoLoginToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    private boolean processAutoLogin(Cookie autoLoginCookie, HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse, ProfileDAO profileDAO) {
        String token = autoLoginCookie.getValue();
        try {
            String userId = autoLoginDAO.getUserIdByToken(token);
            if (userId == null) {
                return false;
            }

            MemberDTO member = memberDAO.getMemberByUserId(userId);
            if (member == null) {
                return false;
            }

            ProfilePictureDTO profile = profileDAO.getProfilePicture(userId);
            
            // 세션 생성 및 사용자 정보 저장
            HttpSession newSession = httpRequest.getSession(true);
            newSession.setAttribute("user", member);
            newSession.setAttribute("profilePath", profile.getFilePath());
            
            // 토큰 갱신
            autoLoginDAO.refreshToken(token);
            return true;

        } catch (Exception e) {
            deleteAutoLoginCookie(httpResponse);
            return false;
        }
    }

    private void deleteAutoLoginCookie(HttpServletResponse httpResponse) {
        Cookie deleteCookie = new Cookie("autoLoginToken", "");
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");
        httpResponse.addCookie(deleteCookie);
    }
}
