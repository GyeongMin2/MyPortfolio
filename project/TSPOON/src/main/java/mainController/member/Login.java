package mainController.member;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.annotation.WebServlet;
import dao.MemberDAO;
import dao.AutoLoginDAO;
import dao.ProfileDAO;
import dto.member.MemberDTO;
import util.DateUtil;
import util.PasswordUtil;
import dto.profile.ProfilePictureDTO;

@WebServlet(name = "Login", value = "/login.do")
public class Login extends HttpServlet {

    private MemberDAO memberDAO;
    private AutoLoginDAO autoLoginDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        memberDAO = new MemberDAO();
        autoLoginDAO = new AutoLoginDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        
        try {   
            MemberDTO member = memberDAO.getMemberForLogin(userId);
            // System.out.println("member : " + member);
            // System.out.println("password : " + password);
            // System.out.println("member.getPassword() : " + member.getPassword());
            // System.out.println("member.getSalt() : " + member.getSalt());
            // System.out.println("verifyPassword : " + PasswordUtil.verifyPassword(password, member.getPassword(), member.getSalt()));
            if (member != null && PasswordUtil.verifyPassword(password, member.getPassword(), member.getSalt())) {
                // 로그인 성공
                // System.out.println("로그인 성공");
                HttpSession session = request.getSession();
                
                MemberDTO fullMemberInfo = memberDAO.getMemberByUserId(userId);
                session.setAttribute("user", fullMemberInfo);
                ProfilePictureDTO profile = profileDAO.getProfilePicture(userId);

                session.setAttribute("profilePath", profile.getFilePath());
                
                // 로그인 상태 유지 체크 확인
                if ("on".equals(request.getParameter("saveid"))) {
                    System.out.println("쿠키생성시작");
                    String token = UUID.randomUUID().toString();
                    String expiresAt = DateUtil.localDateTimeToString(LocalDateTime.now().plusDays(30));
                    
                    autoLoginDAO.saveToken(fullMemberInfo.getUserId(), token, expiresAt);
                    
                    Cookie cookie = new Cookie("autoLoginToken", token);
                    cookie.setMaxAge(30 * 24 * 60 * 60); // 30일
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                
                response.sendRedirect("index.do");
            } else {
                System.out.println("로그인 실패");
                request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
                request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            request.setAttribute("loginError", "로그인 처리 중 오류가 발생했습니다. 다시 시도해 주세요.");
            request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
        }
    }
}