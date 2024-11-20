package mainController.inquiry;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.InquiryDAO;
import dto.member.MemberDTO;
import dto.inquiry.InquiryDTO;

@WebServlet("/deleteInquiry.do")
public class DeleteInquiry extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InquiryDAO inquiryDAO;

    public void init() {
        inquiryDAO = new InquiryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO member = (MemberDTO) session.getAttribute("user");
        
        if (member == null) {
            response.sendRedirect("login.do");
            return;
        }

        int inquiryId = Integer.parseInt(request.getParameter("id"));

        try {
            InquiryDTO inquiry = inquiryDAO.getInquiryById(inquiryId);
            
            if (inquiry == null || !inquiry.getUserId().equals(member.getUserId()) || inquiry.getStatus() != 0) {
                response.sendRedirect("inquiryList.do");
                return;
            }

            inquiryDAO.deleteInquiry(inquiryId);
            response.sendRedirect("inquiryList.do");
        } catch (SQLException e) {
            request.setAttribute("error", "문의 삭제 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}