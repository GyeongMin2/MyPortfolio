package net.haebup.controller.member.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import net.haebup.dao.member.MemberDAO;
import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dto.member.MemberDTO;
// import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.member.payment.CartItemDTO;

@WebServlet("/payments/user/gotoPayments.do")
public class GotoPayment extends HttpServlet {
    private PaymentDAO paymentDAO = new PaymentDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("message", "로그인 후 이용해주세요.");
            request.getRequestDispatcher("/main.do").forward(request, response);
            return;
        }

        String userId = user.getUserId();

        try {
            // 장바구니에서 status == i and 강의 상세 정보 가져오기
            List<CartItemDTO> cartItems = paymentDAO.getCartListWithDetails(userId);

            if (cartItems.isEmpty()) {
                request.setAttribute("msg", "장바구니가 비어있습니다.");
                request.getRequestDispatcher("/main.do").forward(request, response);
                return;
            }

            // 총 결제 금액 계산
            // int totalAmount = cartItems.stream().mapToInt(CartItemDTO::getLecturePrice).sum();
            //마일리지
            int mileage = new MemberDAO().getMileage(userId);
            // 결제 페이지로 장바구니 항목과 총 금액 전달
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("mileage", mileage);
            // request.setAttribute("totalAmount", totalAmount);
            request.getRequestDispatcher("/WEB-INF/user/payments/pay.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "데이터베이스 오류가 발생했습니다.");
            request.getRequestDispatcher("/lecture/common/lectureList.do").forward(request, response);
        }
    }
}
