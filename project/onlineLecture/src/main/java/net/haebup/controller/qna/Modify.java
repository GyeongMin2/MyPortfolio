package net.haebup.controller.qna;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dao.qna.QnaDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.qna.QnaDTO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/modifyQna.do")
public class Modify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		int qnaIdx = Integer.parseInt(request.getParameter("idx"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        QnaDTO qnaDTO = new QnaDTO();
        qnaDTO.setQnaIdx(qnaIdx);
        qnaDTO.setQnaTitle(title);
        qnaDTO.setQnaContent(content);

        QnaDAO qnaDAO = new QnaDAO();
        
        request.setAttribute("qnaIdx", qnaIdx);
        
        int result = 0;

	        try {
	        	result = qnaDAO.updateQna(qnaDTO); 
	        	if(result>0) {
	        		request.setAttribute("msg", "qna 수정이 완료되었습니다.");
	                request.setAttribute("url", "/gotoQnaDetail.do?idx=" + qnaIdx);
	                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
	        	}else {
	        		request.setAttribute("msg", "qna 수정에 실패했습니다..");
	                request.setAttribute("url", "/gotoQnaDetail.do?idx=" + qnaIdx);
	                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
	        	}
	            response.sendRedirect("gotoQnaDetail.do?idx=" + qnaIdx); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
