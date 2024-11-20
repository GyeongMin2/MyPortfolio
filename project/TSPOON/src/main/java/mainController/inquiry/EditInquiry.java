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

@WebServlet("/editInquiry.do")
public class EditInquiry extends HttpServlet {
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
            
            if (inquiry == null || !inquiry.getUserId().equals(member.getUserId())) {
                response.sendRedirect("inquiryList.do");
                return;
            }

            request.setAttribute("inquiry", inquiry);
            request.getRequestDispatcher("/WEB-INF/views/mypage/editInquiry.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "문의 조회 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO member = (MemberDTO) session.getAttribute("user");
        
        if (member == null) {
            response.sendRedirect("login.do");
            return;
        }

        int inquiryId = Integer.parseInt(request.getParameter("inquiryId"));
        String categoryMain = request.getParameter("categoryMain");
        String categorySub = request.getParameter("categorySub");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        try {
            InquiryDTO inquiry = inquiryDAO.getInquiryById(inquiryId);
            
            if (inquiry == null || !inquiry.getUserId().equals(member.getUserId())) {
                response.sendRedirect("inquiryList.do");
                return;
            }

            inquiry.setCategoryMain(categoryMain);
            inquiry.setCategorySub(categorySub);
            inquiry.setTitle(title);
            inquiry.setContent(content);

            inquiryDAO.updateInquiry(inquiry);
            response.sendRedirect("inquiryDetail.do?id=" + inquiryId);
        } catch (SQLException e) {
            request.setAttribute("error", "문의 수정 중 오류가 발생했습니다: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}