package mainController.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;
import dao.MemberDAO;
import dto.member.MemberDTO;

@WebServlet(name = "TeacherLogin", value = "/teacherLogin.do")
public class TeacherLogin extends HttpServlet {

    private MemberDAO memberDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/member/teacherLogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        memberDAO = new MemberDAO();

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        
        try {   
            // 로그인 처리
            MemberDTO member = memberDAO.getMemberForLogin(userId);
            // 비밀번호 확인
            System.out.println("userId: " + userId);
            System.out.println("비밀번호 확인: " + password);
            System.out.println("비밀번호 확인: " + member.getPassword());
            if (member != null && password.equals(member.getPassword())) {
                // 로그인 성공
//                System.out.println("선생님 로그인 성공");
                HttpSession session = request.getSession();

                MemberDTO fullMemberInfo = memberDAO.getMemberByUserId(userId);
                session.setAttribute("user", fullMemberInfo);
                
//                System.out.println("session: " + session.getAttribute("user"));
                
                response.sendRedirect("index.do");
            } else {
//                System.out.println("선생님 로그인 실패");
                request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
                request.getRequestDispatcher("/WEB-INF/views/member/teacherLogin.jsp").forward(request, response);
            }
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
            request.setAttribute("loginError", "로그인 처리 중 오류가 발생했습니다. 다시 시도해 주세요.");
            request.getRequestDispatcher("/WEB-INF/views/member/teacherLogin.jsp").forward(request, response);
        }
    }
}