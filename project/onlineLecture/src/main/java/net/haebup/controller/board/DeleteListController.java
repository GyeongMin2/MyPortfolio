package net.haebup.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/deleteList.do")
public class DeleteListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("다중삭제시작"); 
		String idxParam = request.getParameter("idx");
	        if (idxParam != null && !idxParam.isEmpty()) {
	            String[] idxStrings = idxParam.split(",");
	            int[] idx = new int[idxStrings.length];
	            System.out.println("Received idx: " + idxParam);
	            for (int i = 0; i < idxStrings.length; i++) {
	            	idx[i] = Integer.parseInt(idxStrings[i].trim());
	            }

	            BoardDAO boardDAO = new BoardDAO();
	            try {
	                int deletedCount = boardDAO.deleteByBoardIdxs(idx); 
	                if(deletedCount>0) {
		        		request.setAttribute("msg", "게시글 삭제 완료되었습니다.");
		                request.setAttribute("url", "/gotoTotalPostList.do");
		                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
		        	}else {
		        		 request.setAttribute("msg", "게시글 수정이 실패되었습니다.");
		                 request.setAttribute("url", "javascript:history.back()");
		                 request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
		        	}
	            } catch (SQLException e) {
	                e.printStackTrace();
	                request.setAttribute("error", "게시글 삭제 중 오류가 발생했습니다.");
	                request.getRequestDispatcher("/WEB-INF/admin/post/postList.jsp").forward(request, response);
	            }
	        }
	    }

}
