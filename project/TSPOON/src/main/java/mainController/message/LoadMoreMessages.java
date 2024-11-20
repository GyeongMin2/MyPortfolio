package mainController.message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.MessageContentDAO;
import dto.member.MemberDTO;
import dto.message.MessageDTO;

@WebServlet("/loadMoreMessages.do")
public class LoadMoreMessages extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MessageContentDAO messageContentDAO;

    public void init() {
        messageContentDAO = new MessageContentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MemberDTO member = (MemberDTO) request.getSession().getAttribute("user");
        String userId = member.getUserId();
        String tab = request.getParameter("tab");
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int isSender = "sent".equals(tab) ? 1 : 2;

        try {
            List<MessageDTO> messages = messageContentDAO.getMessagesPaginated(userId, isSender, page, pageSize);
            int totalMessages = messageContentDAO.getTotalMessageCount(userId, isSender);
            int unreadCount = isSender == 2 ? messageContentDAO.getUnreadMessageCount(userId) : 0;
            sendJsonResponse(response, true, "", messages, totalMessages, unreadCount);
        } catch (SQLException e) {
            sendJsonResponse(response, false, "메시지 로드 중 오류가 발생했습니다.", null, 0, 0);
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean success, String message, List<MessageDTO> messages, int totalMessages, int unreadCount) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        StringBuilder json = new StringBuilder("{\"success\": " + success + ", \"message\": \"" + message + "\", \"totalMessages\": " + totalMessages + ", \"unreadCount\": " + unreadCount);
        
        if (messages != null) {
            json.append(", \"messages\": [");
            for (int i = 0; i < messages.size(); i++) {
                MessageDTO msg = messages.get(i);
                json.append("{");
                json.append("\"messageId\": " + msg.getMessageId() + ",");
                json.append("\"messageTitle\": \"" + escapeJsonString(msg.getMessageTitle()) + "\",");
                json.append("\"content\": \"" + escapeJsonString(msg.getContent()) + "\",");
                json.append("\"sendDate\": \"" + msg.getSendDate() + "\",");
                json.append("\"hasFileAttachment\": " + msg.getHasFileAttachment() + ",");
                json.append("\"readStatus\": " + msg.getStatus().getReadStatus() + ",");
                json.append("\"userId\": \"" + escapeJsonString(msg.getStatus().getUserId()) + "\",");
                json.append("\"otherUserId\": \"" + escapeJsonString(msg.getStatus().getIsSender() == 1 ? msg.getReceiverId() : msg.getSenderId()) + "\"");
                json.append("}");
                if (i < messages.size() - 1) {
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
