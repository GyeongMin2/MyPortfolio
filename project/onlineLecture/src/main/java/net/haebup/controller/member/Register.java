package net.haebup.controller.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dao.member.MemberDAO;
import java.sql.SQLException;

@WebServlet("/member/common/register.do")
public class Register extends HttpServlet {
    
    // @Override
    // public void init() throws ServletException {
    //     new MemberDAO();
    // }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("회원가입 요청");
        String userId = req.getParameter("userId");
        String userPwd = req.getParameter("userPwd");
        String userName = req.getParameter("userName");
        String userEmail = req.getParameter("userEmail");
        String userPhone = req.getParameter("userPhone");
        String userBirth = req.getParameter("userBirth");
        String userNickname = req.getParameter("userNickname");

        MemberDTO member = new MemberDTO();
        member.setUserId(userId);
        member.setUserPwd(userPwd);
        member.setUserName(userName);
        member.setUserEmail(userEmail);
        member.setUserPhone(userPhone);
        member.setUserBirth(userBirth);
        member.setUserNickname(userNickname);
        //벨리데이션 체크
        if (userId == null || userPwd == null || userName == null || userEmail == null || userPhone == null || userBirth == null || userNickname == null) {
            req.setAttribute("registerErrorMsg", "빈칸을 채워주세요.");
            req.getRequestDispatcher("/WEB-INF/common/member/register.jsp").forward(req, res);
            return;
        }
        try {
            new MemberDAO().insertUser(member);
            req.setAttribute("registerSuccessMsg", "회원가입이 완료되었습니다.");
            req.getRequestDispatcher("/main.do").forward(req, res);
        } catch (SQLException e) {
            req.setAttribute("registerErrorMsg", "회원가입에 실패하였습니다.");
            req.getRequestDispatcher("/WEB-INF/common/member/register.jsp").forward(req, res);
            e.printStackTrace();
        }

    }
}
