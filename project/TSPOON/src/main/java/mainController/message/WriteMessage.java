package mainController.message;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import util.FileUploadUtil;
import dao.MessageContentDAO;
import dto.message.MessageContentDTO;
import dto.message.MessageFileDTO;
import dto.message.MessageStatusDTO;
import dto.member.MemberDTO;
@WebServlet("/write.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 5,   // 5 MB
    maxRequestSize = 1024 * 1024 * 5 * 5 // 25 MB
)
public class WriteMessage extends HttpServlet {
    private MessageContentDAO messageContentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        messageContentDAO = new MessageContentDAO();
        // System.out.println("WriteMessage 서블릿 초기화 완료");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // System.out.println("WriteMessage doGet 메소드 시작");
        // 메시지 작성 페이지로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/mypage/write_message.jsp").forward(request, response);
        // System.out.println("WriteMessage doGet 메소드 종료: 메시지 작성 페이지로 포워딩 완료");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // System.out.println("WriteMessage doPost 메소드 시작");
        request.setCharacterEncoding("UTF-8");

        // 폼에서 전송된 데이터 추출
        String toUser = request.getParameter("touser");
        // System.out.println("수신자: " + toUser);
        String messageTitle = request.getParameter("messageTitle");
        // System.out.println("메시지 제목: " + messageTitle);
        String content = request.getParameter("content");
        // System.out.println("메시지 내용: " + content);
        MemberDTO member = (MemberDTO) request.getSession().getAttribute("user");
        // System.out.println("발신자: " + member.getUserId());
        String fromUser = member.getUserId();

        try {
            // System.out.println("메시지 DTO 생성 시작");
            // 메시지 내용 DTO 생성
            MessageContentDTO messageContent = new MessageContentDTO();
            messageContent.setMessageTitle(messageTitle);
            messageContent.setContent(content);

            // 발신자 상태 DTO 생성
            MessageStatusDTO senderStatus = new MessageStatusDTO();
            senderStatus.setUserId(fromUser);
            senderStatus.setIsSender(1);
            senderStatus.setReadStatus(1);
            senderStatus.setDeleteStatus(0);

            // 수신자 상태 DTO 생성
            MessageStatusDTO receiverStatus = new MessageStatusDTO();
            receiverStatus.setUserId(toUser);
            receiverStatus.setIsSender(2);
            receiverStatus.setReadStatus(0);
            receiverStatus.setDeleteStatus(0);
            // System.out.println("메시지 DTO 생성 완료");

            // 첨부 파일 처리
            // System.out.println("첨부 파일 처리 시작");
            MessageFileDTO messageFile = null;
            Part filePart = request.getPart("attachment");
            if (filePart != null && filePart.getSize() > 0) {
                // System.out.println("첨부 파일 존재: " + filePart.getSubmittedFileName());
                String filePath = FileUploadUtil.uploadMessageAttachment(request, "attachment");
                if (filePath != null) {
                    messageFile = new MessageFileDTO();
                    messageFile.setFileName(filePart.getSubmittedFileName());
                    messageFile.setFilePath(filePath);
                    messageFile.setFileSize(filePart.getSize());
                    // System.out.println("첨부 파일 정보 설정 완료");
                }
            } else {
                // System.out.println("첨부 파일 없음");
            }

            // System.out.println("메시지 저장 시작");
            // 메시지 저장
            messageContentDAO.insertMessageContent(messageContent, messageFile, senderStatus, receiverStatus);
            // System.out.println("메시지 저장 완료");
            
            // 성공 시 메시지 목록 페이지로 리다이렉트
            request.getSession().setAttribute("successAlert", "메시지 전송이 완료되었습니다.");
            // System.out.println("메시지 전송 성공 알림 설정");
            request.getRequestDispatcher("/WEB-INF/views/mypage/message.jsp").forward(request, response);
            // System.out.println("메시지 목록 페이지로 리다이렉트");
        } catch (SQLException e) {
            // System.out.println("메시지 전송 중 오류 발생: " + e.getMessage());
            // e.printStackTrace();
            // 오류 발생 시 에러 메시지와 함께 메시지 작성 페이지로 포워딩
            request.getSession().setAttribute("errorAlert", "메시지 전송 중 오류가 발생했습니다. 다시 시도해 주세요.");
            // System.out.println("오류 알림 설정");
            request.getRequestDispatcher("/WEB-INF/views/mypage/write_message.jsp").forward(request, response);
            // System.out.println("메시지 작성 페이지로 포워딩 (오류 발생)");
        }catch (Exception e){
            request.getSession().setAttribute("errorAlert", "메시지 전송 중 오류가 발생했습니다. 다시 시도해 주세요.");
            request.getRequestDispatcher("/WEB-INF/views/mypage/write_message.jsp").forward(request, response);
        }
        // System.out.println("WriteMessage doPost 메소드 종료");
    }
}
