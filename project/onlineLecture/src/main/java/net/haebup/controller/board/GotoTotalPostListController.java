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

@WebServlet("/admin/gotoTotalPostList.do")
public class GotoTotalPostListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNoParam = request.getParameter("pageNo");
        String pageSizeParam = request.getParameter("pageSize");

        int pageNo = 1;
        int pageSize = 10;
        
        try {
            if (pageNoParam != null && !pageNoParam.isEmpty()) {
                pageNo = Integer.parseInt(pageNoParam);
            }
            if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
                pageSize = Integer.parseInt(pageSizeParam);
            }
        } catch (NumberFormatException e) {
            // 기본값 사용
        }

        String boardCategory = request.getParameter("category");
        String boardType = request.getParameter("type");
        String searchType = request.getParameter("searchType");
        String searchKeyword = request.getParameter("keyword");
            
        if (boardType == null) boardType = ""; 
        if (searchType == null) searchType = ""; 
        if (searchKeyword == null) searchKeyword = ""; 
        if (boardCategory == null) boardCategory = ""; 

        BoardDAO boardDAO = new BoardDAO();

        try {
            int totalCount = boardDAO.getTotalCount(boardType, searchType, searchKeyword);
            List<BoardDTO> boardList = boardDAO.totalBoardList(pageSize, (pageNo - 1) * pageSize, 
                                                             boardType, searchType, searchKeyword, boardCategory);

            Pagination pagination = new Pagination(pageNo, pageSize, totalCount, 10);

            request.setAttribute("boardList", boardList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("totalCount", totalCount);
            
            // 검색 조건 유지를 위한 속성 설정
            request.setAttribute("type", boardType);
            request.setAttribute("searchType", searchType);
            request.setAttribute("keyword", searchKeyword);
            request.setAttribute("category", boardCategory);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "게시판 정보를 가져오는 도중 오류가 발생했습니다.");
        }

        request.getRequestDispatcher("/WEB-INF/admin/post/postList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}