package net.haebup.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.utils.PaginationUtil.Pagination;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/gotoPostList.do")
public class GotoPostList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//category 강의 코드
		//boardWriter 선생님
		String pageNoParam = request.getParameter("pageNo");
		String pageSizeParam = request.getParameter("pageSize");
		String boardType = request.getParameter("type");  
		String boardCategory = request.getParameter("category"); 
		System.out.println("boardCategory : "+boardCategory);
		String boardWriter = request.getParameter("teacherId");  

		int pageNo = pageNoParam != null ? Integer.parseInt(pageNoParam) : 1;      
		int pageSize = pageSizeParam != null ? Integer.parseInt(pageSizeParam) : 10; 

		// 검색 유형 (title, writer, title+content)
		String searchType = request.getParameter("searchType");   
		String searchKeyword = request.getParameter("searchKeyword"); 

		BoardDAO boardDAO = new BoardDAO();

		try {
		    List<BoardDTO> boardList;
		    int totalCount;

		    if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
		        totalCount = boardDAO.getTotalCount(boardType, boardCategory, searchType, searchKeyword, boardWriter);
		        boardList = boardDAO.getBoardListByPage(pageNo, pageSize, boardType, boardCategory, boardWriter, searchType, searchKeyword);
		    } else {
		        totalCount = boardDAO.getTotalCount(boardType, boardCategory, boardWriter);
		        boardList = boardDAO.getBoardListByPage(pageNo, pageSize, boardType, boardCategory, boardWriter, null, null);
		    }

//		    System.out.println("========게시판 리스트 확인==========");
//		    for (BoardDTO boardDTO : boardList) {
//		        System.out.println("idx : " + boardDTO.getBoardIdx());
//		        System.out.println("title  : " + boardDTO.getBoardTitle());
//		        System.out.println("writer : " + boardDTO.getBoardWriter());
//		        System.out.println("reqdate : " + boardDTO.getBoardRegdate());
//		        System.out.println("type : " + boardDTO.getBoardType());
//		        System.out.println("category : " + boardDTO.getBoardCategory());
//		    }
//		    System.out.println("====================");

		    // 10 -> blockSize 
		    Pagination pagination = new Pagination(pageNo, pageSize, totalCount, 10);
		    
		    request.setAttribute("boardType", boardType);
		    System.out.println(boardType);
		    request.setAttribute("boardList", boardList);         
		    System.out.println(boardList);
		    request.setAttribute("category", boardCategory);       
		    System.out.println(boardCategory);
		    request.setAttribute("pagination", pagination);       

//		    String url = "";
		    switch(boardType) {
		        case "P":  
		            request.getRequestDispatcher(request.getContextPath() + "WEB-INF/common/post/list.jsp").forward(request, response);
		            break;
		        case "D": 
		            request.getRequestDispatcher(request.getContextPath() + "WEB-INF/common/filePost/fileList.jsp").forward(request, response);
		            break;
		        case "N":  
		            request.getRequestDispatcher(request.getContextPath() + "WEB-INF/common/noticePost/noticeList.jsp").forward(request, response);
		            break;
		        case "C":  
		            request.getRequestDispatcher(request.getContextPath() + "WEB-INF/common/lecture/lectureNoticeList.jsp").forward(request, response);
		            break;
		        case "R":  
		            request.getRequestDispatcher(request.getContextPath() + "WEB-INF/common/lecture/lectureReview.jsp").forward(request, response);
		            break;
		    }
//		    if (boardCategory != null) {
//		        url += "?category=" + boardCategory;
//		    }
//		    if (boardWriter != null) {
//		        url += "?writer=" + boardWriter;
//		    }

		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
