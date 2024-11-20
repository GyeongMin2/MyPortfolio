package mainController.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dao.MemberDAO;
import dto.member.MemberDTO;
import util.PasswordUtil;

@WebServlet(name = "EditMember", value = "/editMember.do")
public class EditMember extends HttpServlet {
    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("errorAlert", "로그인이 필요합니다.");
            request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/member/edit_myinfo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("errorAlert", "로그인이 필요합니다.");
            request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
            return;
        }

        MemberDTO sessionMember = (MemberDTO) session.getAttribute("user");
        String inputPassword = request.getParameter("password");

        try {
            MemberDTO storedMember = memberDAO.getMemberForLogin(sessionMember.getUserId());
            
            if (storedMember != null && PasswordUtil.verifyPassword(inputPassword, storedMember.getPassword(), storedMember.getSalt())) {
                // 비밀번호 확인 성공, 개인정보 수정 페이지로 이동
                request.setAttribute("member", sessionMember);
                request.getRequestDispatcher("/WEB-INF/views/member/edit_myinfo_page.jsp").forward(request, response);
            } else {
                request.setAttribute("errorAlert", "비밀번호가 일치하지 않습니다.");
                request.getRequestDispatcher("/WEB-INF/views/member/edit_myinfo.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorAlert", "회원 정보 확인 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/member/edit_myinfo.jsp").forward(request, response);
        }
    }
}
