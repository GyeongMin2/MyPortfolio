package net.haebup.controller.qna;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dao.qna.QnaDAO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.qna.QnaDTO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteQna.do")
public class DeleteQna extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int qnaIdx = Integer.parseInt(request.getParameter("idx"));
		String qnaType = request.getParameter("type");
		
		QnaDAO qnaDAO = new QnaDAO();
		QnaDTO qnaDTO = new QnaDTO();
		String writerId = null; 
        
        try {
        	qnaDTO = qnaDAO.selectQnaDetail(qnaIdx);
        	if(qnaDTO == null) {
                response.getWriter().print("<script>alert('QNA게시글을 찾을 수 없습니다.'); location.href='javascript:history.back();';</script>");
                return;
            }
        	
            writerId = qnaDTO.getQnaWriter(); 
            MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
            String loginId = user != null ? user.getUserId() : null; 
            
//            if (loginId == null || !writerId.equals(loginId)) {
//                response.getWriter().print("<script>alert('삭제 권한이 없습니다.'); location.href='javascript:history.back();';</script>");
//                return;
//            }

            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String msg = "";
            int result = qnaDAO.deleteQna(qnaIdx);
            if (result > 0) {
            	msg="QNA 게시글 삭제 성공";
                response.getWriter().print("<script>alert('"+ msg +"'); location.href='/gotoQnaList.do?type=" + qnaType + "';</script>");
            } else {
            	msg="QNA 게시글 삭제 실패";
                response.getWriter().print("<script>alert('"+ msg +"'); location.href='javascript:history.back();';</script>");
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
