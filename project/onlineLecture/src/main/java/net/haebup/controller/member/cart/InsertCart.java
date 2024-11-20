package net.haebup.controller.member.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dto.member.MemberDTO;
@WebServlet("/lecture/user/insertCart.do")
public class InsertCart extends HttpServlet {
    private PaymentDAO paymentDAO = new PaymentDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        if (user == null) {
            out.print("{\"status\":\"error\",\"message\":\"로그인이 필요합니다.\"}");
            return;
        }
        String userId = user.getUserId();

        String lectureCode = request.getParameter("lectureCode");

        if (lectureCode == null || lectureCode.trim().isEmpty()) {
            out.print("{\"status\":\"error\",\"message\":\"강의 코드가 유효하지 않습니다.\"}");
            return;
        }

        try {
            // 먼저 장바구니에 이미 있는지 확인
            if (paymentDAO.isLectureInCart(userId, lectureCode)) {
                out.print("{\"status\":\"error\",\"message\":\"이미 장바구니에 있는 강의입니다.\"}");
                return;
            }

            // 장바구니에 없다면 추가
            int result = paymentDAO.addCart(userId, lectureCode);
            if (result > 0) {
                out.print("{\"status\":\"success\",\"message\":\"강의가 장바구니에 추가되었습니다.\"}");
            } else {
                out.print("{\"status\":\"error\",\"message\":\"장바구니 추가에 실패했습니다.\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"데이터베이스 오류가 발생했습니다.\"}");
        }
    }
}
