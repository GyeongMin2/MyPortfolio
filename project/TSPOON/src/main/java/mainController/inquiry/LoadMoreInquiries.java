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
import dto.inquiry.InquiryDTO;
import dto.member.MemberDTO;

@WebServlet("/loadMoreInquiries.do")
public class LoadMoreInquiries extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InquiryDAO inquiryDAO;

    public void init() {
        inquiryDAO = new InquiryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        MemberDTO member = (MemberDTO) session.getAttribute("user");
        
        if (member == null) {
            sendJsonResponse(response, false, "로그인이 필요합니다.", null);
            return;
        }

        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = 5;

        try {
            List<InquiryDTO> inquiries = inquiryDAO.getInquiriesByUserId(member.getUserId(), page, pageSize);
            sendJsonResponse(response, true, "", inquiries);
        } catch (SQLException e) {
            // e.printStackTrace();
            sendJsonResponse(response, false, "문의 조회 중 오류가 발생했습니다: " + e.getMessage(), null);
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message, List<InquiryDTO> inquiries) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        StringBuilder json = new StringBuilder("{\"success\": " + success + ", \"message\": \"" + message + "\"");
        
        if (inquiries != null) {
            json.append(", \"inquiries\": [");
            for (int i = 0; i < inquiries.size(); i++) {
                InquiryDTO inquiry = inquiries.get(i);
                json.append("{");
                json.append("\"inquiryId\": " + inquiry.getInquiryId() + ",");
                json.append("\"categoryMain\": \"" + escapeJsonString(inquiry.getCategoryMain()) + "\",");
                json.append("\"title\": \"" + escapeJsonString(inquiry.getTitle()) + "\",");
                json.append("\"submitDate\": \"" + inquiry.getSubmitDate() + "\",");
                json.append("\"status\": " + inquiry.getStatus());
                json.append("}");
                if (i < inquiries.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
        }
        
        json.append("}");
        response.getWriter().write(json.toString());
    }

    private String escapeJsonString(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}