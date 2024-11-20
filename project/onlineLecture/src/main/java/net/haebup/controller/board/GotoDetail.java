package net.haebup.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dao.board.comment.CommentDAO;
import net.haebup.dao.board.file.FileDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.board.comment.BoardCommentDTO;
import net.haebup.dto.board.file.FileDTO;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/gotoPostDetail.do")
public class GotoDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardIdx = Integer.parseInt(request.getParameter("idx"));
		String boardType = request.getParameter("type");
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = null;

		try {
		    boardDTO = boardDAO.getBoardDetail(boardIdx);
		    if (boardDTO != null) {
		        request.setAttribute("boardDTO", boardDTO);
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		 CommentDAO commentDAO = new CommentDAO();
	        try {
	            List<BoardCommentDTO> commentList = commentDAO.selectCommentList(boardIdx);
	            // 출력 값 확인
//	            for (BoardCommentDTO comment : commentList) {
//	            	System.out.println("-----GotoDetail-----"); 
//	                System.out.println("Comment Index: " + comment.getCommentIdx());
//	                System.out.println("Board Index: " + comment.getPostIdx());
//	                System.out.println("Comment Content: " + comment.getCommentContent());
//	                System.out.println("Comment Registration Date: " + comment.getCommentRegdate());
//	                System.out.println("User ID: " + comment.getUserId());
//	                System.out.println("-----GotoDetail E-----"); 
//	            }
	            
	            request.setAttribute("commentList", commentList);
	            
	            int commentCount = commentDAO.getCount(boardIdx);
	            request.setAttribute("commentCount", commentCount); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        

	        
	        FileDAO fileDAO = new FileDAO();
	        try {
	            List<FileDTO> fileList = fileDAO.selectFileByBoardIdx(boardIdx);
	            if (fileList != null && !fileList.isEmpty()) {
	                for (FileDTO file : fileList) {
	                    String encodedFilePath = URLEncoder.encode(file.getFilePath(), "UTF-8");
	                    String encodedFileName = URLEncoder.encode(file.getFileName(), "UTF-8");
	                    
	                    file.setFilePath(encodedFilePath);
	                    file.setFileName(encodedFileName);
	                }
	                request.setAttribute("fileList", fileList);  
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
        request.setAttribute("boardType", boardType);  
		request.getRequestDispatcher("WEB-INF/common/post/detail.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
