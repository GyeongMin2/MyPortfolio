package mainController.message;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import dao.MessageContentDAO;
import dto.message.MessageDTO;
import dto.member.MemberDTO;

@WebServlet("/messageDetail.do")
public class MessageDetail extends HttpServlet {
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
            session.setAttribute("errorAlert", "로그인 후 이용해 주세요.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        int messageId;
        try {
            messageId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 메시지 ID입니다.");
            return;
        }

        try {
            MessageDTO message = messageContentDAO.getMessageById(messageId, userId);

            if (message == null) {
                session.setAttribute("errorAlert", "메시지를 찾을 수 없습니다.");   
                response.sendRedirect(request.getHeader("Referer"));
                return;
            }

            System.out.println("Current user ID: " + userId);
            System.out.println("Message receiver ID: " + message.getReceiverId());
            System.out.println("Message sender ID: " + message.getSenderId());
            System.out.println("Message read status: " + message.getStatus().getReadStatus());

            // 현재 사용자가 수신자이고 메시지를 처음 읽는 경우
            if (userId.equals(message.getReceiverId()) && message.getStatus().getReadStatus() == 0) {
                System.out.println("Updating read status for message: " + messageId);
                messageContentDAO.updateMessageReadStatus(messageId, userId);
                System.out.println("Read status updated successfully");
                message = messageContentDAO.getMessageById(messageId, userId); // 업데이트된 정보로 다시 조회
            } else {
                System.out.println("Not updating read status. Conditions not met.");
            }

            request.setAttribute("message", message);
            request.getRequestDispatcher("/WEB-INF/views/mypage/message_detail.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace(); // 로그 기록을 위해 추가
            session.setAttribute("errorAlert", "메시지 조회 중 오류가 발생했습니다.");
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}
