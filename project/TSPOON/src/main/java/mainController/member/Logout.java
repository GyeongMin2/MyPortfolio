package mainController.member;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.annotation.WebServlet;
import dao.AutoLoginDAO;

@WebServlet(name = "Logout", value = "/logout.do")
public class Logout extends HttpServlet {

    private AutoLoginDAO autoLoginDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        autoLoginDAO = new AutoLoginDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 자동 로그인 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("autoLoginToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    System.out.println("로그아웃 쿠키삭제시작");
                    try {
                        autoLoginDAO.deleteToken(token);
                    } catch (Exception e) {
                        System.out.println(e.getMessage()+"로그아웃 쿠키삭제중 오류");
                        // 로그 처리
                        // e.printStackTrace();
                    }
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/index.do");
    }
}
