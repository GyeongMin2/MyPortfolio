package net.haebup.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.member.MemberDTO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deletePost.do")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int boardIdx = Integer.parseInt(request.getParameter("idx"));
        String boardType = request.getParameter("type");
        System.out.println("삭제시 타입확인 : "+ boardType);
//        String boardCategory = request.getParameter("category"); 

        BoardDAO boardDAO = new BoardDAO();
        BoardDTO boardDTO = null; 
        String writerId = null; 

        try {
            // 게시글 정보 가져오기
            boardDTO = boardDAO.getBoardDetail(boardIdx);
            if (boardDTO == null) {
                response.getWriter().print("<script>alert('게시글을 찾을 수 없습니다.'); location.href='javascript:history.back();';</script>");
                return;
            }

            writerId = boardDTO.getBoardWriter(); 
            MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
            String loginId = user != null ? user.getUserId() : null; 

            // 삭제 권한 확인
            if (loginId == null || !writerId.equals(loginId)) {
                response.getWriter().print("<script>alert('삭제 권한이 없습니다.'); location.href='javascript:history.back();';</script>");
                return;
            }

            // 게시글 삭제
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String msg = "";
            int result = boardDAO.deleteByBoardIdx(boardIdx);
            if (result > 0) {
            	msg="게시글 삭제 성공";
                response.getWriter().print("<script>alert('"+ msg +"'); location.href='/gotoPostList.do?type=" + boardType + "';</script>");
            } else {
            	msg="게시글 삭제 실패";
                response.getWriter().print("<script>alert('"+ msg +"'); location.href='javascript:history.back();';</script>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  

}