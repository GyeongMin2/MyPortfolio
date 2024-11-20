package mainController.inquiry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.InquiryDAO;
import dto.member.MemberDTO;
import dto.inquiry.InquiryDTO;

@WebServlet("/inquiryList.do")
public class InquiryList extends HttpServlet {
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

        try {
            List<InquiryDTO> inquiries = inquiryDAO.getInquiriesByUserId(member.getUserId(), 5, 0);
            if (inquiries == null) {
                session.setAttribute("error", "문의 목록이 없습니다.");
                response.sendRedirect("inquiry.do");
                return;
            }
            request.setAttribute("inquiries", inquiries);
            request.getRequestDispatcher("/WEB-INF/views/mypage/inquiryList.jsp").forward(request, response);
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            e.getStackTrace();
            request.setAttribute("error", "문의 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/common/error.jsp").forward(request, response);
        }
    }
}