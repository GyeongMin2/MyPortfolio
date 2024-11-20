package net.haebup.controller.qna.qnaComment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.comment.CommentDAO;
import net.haebup.dao.qna.qnaComment.QnaCommentDAO;
import net.haebup.dto.board.comment.BoardCommentDTO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.qna.qnaComment.QnaCommentDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/qnaCommentWrite.do")
public class QnaCommentWriterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int qnaIdx = Integer.parseInt(request.getParameter("qnaIdx"));
	    String commentQnaContent = request.getParameter("commentQnaContent");
	    MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
        
	    if (user == null) {
            request.setAttribute("msg", "작성 권한이 없습니다. 로그인 후 다시 시도해 주세요.");
            request.setAttribute("url", "/gotoQnaDetail.do?idx=" + qnaIdx);
            request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
            return; 
        }
	    String userId = user.getUserId();    

	    System.out.println("qnaIdx: " + qnaIdx);
	    System.out.println("commentQnaContent: " + commentQnaContent);

	    QnaCommentDTO qnacommentDTO = new QnaCommentDTO();
	    qnacommentDTO.setQnaIdx(qnaIdx);
	    qnacommentDTO.setQnaCommentContent(commentQnaContent);
	    qnacommentDTO.setQnaCommentWriter(userId);

	    QnaCommentDAO qnacommentDAO = new QnaCommentDAO();
	    try {
	        int result = qnacommentDAO.insertQnaComment(qnacommentDTO); 
	        System.out.println("Insert Result: " + result); 
	        if (result > 0) {
                request.setAttribute("msg", "댓글이 성공적으로 등록되었습니다.");
            } else {
                request.setAttribute("msg", "댓글 등록에 실패했습니다. 다시 시도해 주세요.");
            }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("errorMessage", "댓글 등록 중 오류가 발생하였습니다.");
	    }

	    request.setAttribute("url", "/gotoQnaDetail.do?idx=" + qnaIdx);
        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response); 
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
