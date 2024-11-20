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

@WebServlet("/gotoPostModify.do")
public class GotoModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardIdx = Integer.parseInt(request.getParameter("idx")); 
		MemberDTO memberDto = (MemberDTO) request.getSession().getAttribute("user");
        BoardDAO boardDAO = new BoardDAO();
        
        try {
            BoardDTO boardDTO = boardDAO.getBoardDetail(boardIdx); 
            
            if (boardDTO != null) {
                String boardType = boardDTO.getBoardType();

                if (memberDto != null) {
                    String userType = memberDto.getUserType();
                    String userId = memberDto.getUserId();
                    String boardWriterId = boardDTO.getBoardWriter(); 
                    // 아이디 확인
                    if (boardWriterId.equals(userId)) {
                    // 권한 검사
                    if (boardWriterId.equals(memberDto.getUserId()) || userType.equals("A")) {
                        // 수정 페이지로 이동
                        request.setAttribute("boardDTO", boardDTO); 
                        request.getRequestDispatcher("/WEB-INF/common/post/modify.jsp").forward(request, response);
                    } else if (boardType.equals("N") && !userType.equals("A")) {
                        // N 타입 게시글은 A 등급만 수정 가능
                        request.setAttribute("msg", "공지사항 수정 권한이 없습니다."); 
                        request.setAttribute("url", "javascript:history.back();"); 
                        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
                    } else if (boardType.equals("R") && !userType.startsWith("S")) {
                        // R 타입 게시글은 S로 시작하는 등급만 수정 가능
                        request.setAttribute("msg", "강의 후기 수정 권한이 없습니다."); 
                        request.setAttribute("url", "javascript:history.back();"); 
                        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
                    } else if (boardType.equals("D") && !userType.equals("T") && !userType.equals("A")) {
                        // D 타입 게시글은 T, A 등급만 수정 가능
                        request.setAttribute("msg", "자료실 수정 권한이 없습니다."); 
                        request.setAttribute("url", "javascript:history.back();"); 
                        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
                    } else {
                        request.setAttribute("msg", "수정 권한이 없습니다."); 
                        request.setAttribute("url", "javascript:history.back();"); 
                        request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
                    }
                } else {
                	request.setAttribute("msg", "작성자 본인만 수정이 가능합니다"); 
                    request.setAttribute("url", "/main.do"); 
                    request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
                }
			}
		}
            else {
            	
            	request.setAttribute("msg", "로그인이 필요합니다.");
                request.setAttribute("url", "/main.do"); 
                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
