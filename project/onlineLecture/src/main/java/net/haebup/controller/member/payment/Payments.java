package net.haebup.controller.member.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dao.member.MemberDAO;
import net.haebup.dto.member.MemberDTO;

@WebServlet("/payments/user/payments.do")
public class Payments extends HttpServlet {
    private PaymentDAO paymentDAO = new PaymentDAO();
    private MemberDAO memberDAO = new MemberDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO user = (MemberDTO) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/main.do");
            return;
        }

        String userId = user.getUserId();
        String[] selectedLectures = request.getParameterValues("selectedLectures");

        if (selectedLectures == null || selectedLectures.length == 0) {
            request.setAttribute("message", "선택된 강의가 없습니다.");
            request.getRequestDispatcher("/payments/user/gotoPayments.do").forward(request, response);
            return;
        }

        List<String> lectureCodes = Arrays.asList(selectedLectures);

        try {
            // 총 결제 금액 계산
            int totalAmount = calculateTotalAmount(userId, lectureCodes);

            // 사용자의 마일리지 확인
            int userMileage = memberDAO.getMileage(userId);

            if (userMileage < totalAmount) {
                request.setAttribute("message", "마일리지가 부족합니다.");
                request.getRequestDispatcher("/payments/user/gotoPayments.do").forward(request, response);
                return;
            }

            // 결제 처리 및 상태 업데이트
            int updatedCount = paymentDAO.paySelectedLectures(userId, lectureCodes);

            if (updatedCount > 0) {
                // 마일리지 차감
                int newMileage = userMileage - totalAmount;
                memberDAO.updateMileage(userId, newMileage);
                
                request.setAttribute("msg", updatedCount + "개의 강의가 결제되었습니다.");
                request.setAttribute("url", "/payments/user/goPay.do");
            } else {
                request.setAttribute("msg", "결제 처리 중 오류가 발생했습니다.");
                request.setAttribute("url", "/payments/user/gotoPayments.do");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "데이터베이스 오류가 발생했습니다.");
        }

        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
    }

    private int calculateTotalAmount(String userId, List<String> lectureCodes) throws SQLException {
        return paymentDAO.calculateTotalAmount(userId, lectureCodes);
    }
}
