package net.haebup.controller.member.payment;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.member.payment.PaymentDAO;

@WebServlet("/payments/user/deleteCartItem.do")
public class DeleteCartItem extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            int paymentIdx = Integer.parseInt(request.getParameter("paymentIdx"));
            PaymentDAO paymentDAO = new PaymentDAO();
            int result = paymentDAO.deleteCartItem(paymentIdx);
            
            String jsonResponse = "{\"success\":true,\"message\":\"삭제되었습니다.\"}";
            response.getWriter().write(jsonResponse);
            
        } catch (Exception e) {
            String jsonResponse = "{\"success\":false,\"message\":\"삭제 중 오류가 발생했습니다.\"}";
            response.getWriter().write(jsonResponse);
        }
    }
}