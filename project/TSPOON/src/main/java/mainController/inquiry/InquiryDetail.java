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
import dto.inquiry.InquiryDTO;
import dto.member.MemberDTO;

@WebServlet("/inquiryDetail.do")
public class InquiryDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InquiryDAO inquiryDAO;

    public void init() {
        inquiryDAO = new InquiryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO member = (MemberDTO) session.getAttribute("user");
        
        if (member == null) {
            session.setAttribute("errorAlert", "로그인이 필요합니다.");
            response.sendRedirect("login.do");
            return;
        }

        int inquiryId = Integer.parseInt(request.getParameter("id"));

        try {
            InquiryDTO inquiry = inquiryDAO.getInquiryById(inquiryId);
            
            if (inquiry == null || !inquiry.getUserId().equals(member.getUserId())) {
                response.sendRedirect("inquiryList.do");
                return;
            }

            request.setAttribute("inquiry", inquiry);
            request.getRequestDispatcher("/WEB-INF/views/mypage/inquiryDetail.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            request.setAttribute("error", "문의 상세 조회 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
