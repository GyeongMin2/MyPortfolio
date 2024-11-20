package mainController.inquiry;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dto.inquiry.InquiryDTO;
import dao.InquiryDAO;
import dto.member.MemberDTO;



@WebServlet("/inquiry.do")
public class Inquiry extends HttpServlet {
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

        request.getRequestDispatcher("/WEB-INF/views/mypage/inquiry.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO member = (MemberDTO) session.getAttribute("user");
        
        if (member == null) {
            response.sendRedirect("login.do");
            return;
        }

        String categoryMain = request.getParameter("categoryMain");
        String categorySub = request.getParameter("categorySub");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        InquiryDTO inquiry = new InquiryDTO();
        inquiry.setUserId(member.getUserId());
        inquiry.setCategoryMain(categoryMain);
        inquiry.setCategorySub(categorySub);
        inquiry.setTitle(title);
        inquiry.setContent(content);

        try {
            inquiryDAO.insertInquiry(inquiry);
            response.sendRedirect("inquiryList.do");
        } catch (SQLException e) {
            request.setAttribute("error", "문의 등록 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}