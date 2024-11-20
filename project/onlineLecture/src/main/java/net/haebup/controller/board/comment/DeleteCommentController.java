package net.haebup.controller.board.comment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.comment.CommentDAO;
import net.haebup.dto.board.comment.BoardCommentDTO;
import net.haebup.dto.member.MemberDTO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/commentDelete.do")
public class DeleteCommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int commentIdx = Integer.parseInt(request.getParameter("commentIdx"));
	        String boardIdx = request.getParameter("boardIdx");
	        MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
	        String userId = user.getUserId(); 

	        CommentDAO commentDAO = new CommentDAO();
	        try {
	            BoardCommentDTO comment = commentDAO.selectCommentIdx(commentIdx);
	            if (comment.getUserId().equals(userId)) {
	                commentDAO.deleteComment(commentIdx); 
	                request.setAttribute("msg", "댓글 삭제 완료.");
	                request.setAttribute("url", "/gotoPostDetail.do?idx="+boardIdx);
	                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
	            } else {
	            	request.setAttribute("msg", "댓글 삭제 실패");
	                request.setAttribute("url", "/gotoPostDetail.do?idx="+boardIdx);
	                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

}
