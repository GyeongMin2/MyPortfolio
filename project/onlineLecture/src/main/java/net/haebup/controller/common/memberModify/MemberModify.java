package net.haebup.controller.common.memberModify;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import net.haebup.dto.member.MemberDTO;
import java.sql.SQLException;
import net.haebup.dao.member.MemberDAO;

@WebServlet("/member/common/modify.do")
public class MemberModify extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        MemberDTO memberDto = null;
        MemberDTO loginUser = (MemberDTO) req.getSession().getAttribute("user");
        try {
            memberDto = new MemberDAO().getUserInfo(loginUser.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("msg", "회원정보조회 중 오류가 발생하였습니다.");
        }
    //     	// 회원정보수정 ( 닉네임, 이메일, 핸드폰 가능, 멤버타입 변경 가능 ) (관리자는 타입변경 가능)
	// public int updateUserInfo(MemberDTO memberDto) throws SQLException {
	// 	String sql = "UPDATE tbl_member SET user_nickname =? , user_phone =? , user_email=? , user_type=? WHERE user_id = ? ";
	// 	try (Connection conn = DBConnPool.getConnection();
	// 			DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { memberDto.getUserNickname(),
	// 					memberDto.getUserPhone(), memberDto.getUserEmail(), memberDto.getUserId() })) {
	// 		return dbUtil.executeUpdate();
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 		throw new SQLException("회원정보수정 중 오류가 발생하였습니다." + e);
	// 	}
	// }

        try{
            int result = new MemberDAO().updateUserInfo(memberDto);
            if (result > 0) {
                req.setAttribute("msg", "회원정보수정이 완료되었습니다.");
                req.getSession().setAttribute("user", memberDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("msg", "회원정보수정 중 오류가 발생하였습니다.");
        }
        req.getRequestDispatcher("/WEB-INF/common/member/modify.jsp").forward(req, res);
    }
}
