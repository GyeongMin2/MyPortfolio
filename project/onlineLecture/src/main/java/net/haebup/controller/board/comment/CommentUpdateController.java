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
@WebServlet("/commentUpdate.do")
public class CommentUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int commentIdx = Integer.parseInt(request.getParameter("commentIdx"));
        String boardIdx = request.getParameter("boardIdx");
        String updatedContent = request.getParameter("commentContent");
        MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
        String userId = user.getUserId(); 

        System.out.println("게시글 번호 : "+boardIdx);
        System.out.println("댓글 수정 서블릿: " + commentIdx);
        System.out.println("세션 사용자 ID: " + userId);

        CommentDAO commentDAO = new CommentDAO();
        try {
            // 댓글 조회
            BoardCommentDTO comment = commentDAO.selectCommentIdx(commentIdx); 
            if (comment == null) {
                System.out.println("댓글을 찾을 수 없습니다: " + commentIdx);
                request.setAttribute("msg", "댓글이 존재하지 않습니다.");
                request.setAttribute("url", "/gotoPostDetail.do?idx="+boardIdx);
                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
                return;
            }

            // 사용자 ID 확인
            if (comment.getUserId().equals(userId)) { 
                comment.setCommentContent(updatedContent);
                
                commentDAO.updateComment(comment); 
                request.setAttribute("msg", "댓글 수정 완료.");
                request.setAttribute("url", "/gotoPostDetail.do?idx="+boardIdx);
                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "댓글 수정 실패: 권한이 없습니다.");
                request.setAttribute("url", "/gotoPostDetail.do?idx="+boardIdx);
                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "댓글 수정 중 오류가 발생했습니다.");
            request.setAttribute("url", "/gotoQnaDetail.do");
            request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
        }
    }
	 
	 

}
