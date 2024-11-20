package net.haebup.controller.qna;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dao.board.comment.CommentDAO;
import net.haebup.dao.board.file.FileDAO;
import net.haebup.dao.qna.QnaDAO;
import net.haebup.dao.qna.qnaComment.QnaCommentDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.board.comment.BoardCommentDTO;
import net.haebup.dto.board.file.FileDTO;
import net.haebup.dto.qna.QnaDTO;
import net.haebup.dto.qna.qnaComment.QnaCommentDTO;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/gotoQnaDetail.do")
public class GotoQnaDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int qnaIdx = Integer.parseInt(request.getParameter("idx"));
		QnaDAO qnaDAO = new QnaDAO();
		QnaDTO qnaDTO = null;

		try {
		    qnaDTO = qnaDAO.selectQnaDetail(qnaIdx);
		    if (qnaDTO != null) {
		        request.setAttribute("qnaDTO", qnaDTO);
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		 QnaCommentDAO qnacommentDAO = new QnaCommentDAO();
	        try {
	            List<QnaCommentDTO> commentQnaList = qnacommentDAO.selectQnaCommentList(qnaIdx);
	             //출력 값 확인
	            for (QnaCommentDTO comment : commentQnaList) {
	            	System.out.println("-----GotoDetail-----"); 
	                System.out.println("Comment Index: " + comment.getQnaCommentIdx());
	                System.out.println("Board Index: " + comment.getQnaIdx());
	                System.out.println("Comment Content: " + comment.getQnaCommentContent());
	                System.out.println("Comment Registration Date: " + comment.getQnaCommentRegdate());
	                System.out.println("User ID: " + comment.getQnaCommentWriter());
	                System.out.println("-----GotoDetail E-----"); 
	            }
	            
	            request.setAttribute("commentQnaList", commentQnaList);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
		request.getRequestDispatcher("WEB-INF/common/inquiy/detail.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
