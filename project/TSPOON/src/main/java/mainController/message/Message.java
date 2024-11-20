package mainController.message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.MessageContentDAO;
import dto.message.MessageDTO;
import dto.member.MemberDTO;
@WebServlet("/message.do")
public class Message extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MessageContentDAO messageContentDAO;

    public void init() {
        messageContentDAO = new MessageContentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        MemberDTO member = (MemberDTO) session.getAttribute("user");
        String userId = member.getUserId();

        if (userId == null) {
            response.sendRedirect("login.do");
            session.setAttribute("errorAlert", "로그인 상태가 아닙니다. 로그인 후 이용해주세요.");
            return;
        }

        String tab = request.getParameter("tab");
        if (tab == null || tab.isEmpty()) {
            tab = "received";
        }
        int page = 1;
        int pageSize = 5;

        try {
            List<MessageDTO> messages;
            int totalMessages;
            int unreadCount = 0;
            if ("sent".equals(tab)) {
                messages = messageContentDAO.getMessagesPaginated(userId, 1, page, pageSize);
                totalMessages = messageContentDAO.getTotalMessageCount(userId, 1);
            } else {
                messages = messageContentDAO.getMessagesPaginated(userId, 2, page, pageSize);
                totalMessages = messageContentDAO.getTotalMessageCount(userId, 2);
                unreadCount = messageContentDAO.getUnreadMessageCount(userId);
            }

            int totalPages = (int) Math.ceil((double) totalMessages / pageSize);

            request.setAttribute("messages", messages);
            request.setAttribute("currentTab", tab);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("unreadCount", unreadCount);
            request.setAttribute("totalMessages", totalMessages);

            request.getRequestDispatcher("/WEB-INF/views/mypage/message.jsp").forward(request, response);
        } catch (SQLException e) {
            session.setAttribute("errorAlert", "쪽지 목록을 불러오는 중 오류가 발생했습니다.");
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String[] messageIds = request.getParameterValues("messageIds");

        if (messageIds == null || messageIds.length == 0) {
            session.setAttribute("errorAlert", "선택된 쪽지가 없습니다.");
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }

        try {
            switch (action) {
                case "markAsRead":
                    messageContentDAO.markMessagesAsRead(messageIds);
                    session.setAttribute("successAlert", "선택한 쪽지를 읽음 처리했습니다.");
                    break;
                case "delete":
                    int deletedCount = messageContentDAO.deleteMessages(messageIds);
                    session.setAttribute("successAlert", deletedCount + "개의 쪽지를 삭제했습니다.");
                    break;
                default:
                    session.setAttribute("errorAlert", "유효하지 않은 작업입니다.");
                    break;
            }
            response.sendRedirect(request.getHeader("Referer"));
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorAlert", "쪽지 처리 중 오류가 발생했습니다: " + e.getMessage());
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}
