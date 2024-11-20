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

@WebServlet("/gotoQnaModify.do")
public class GotoModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int qnaIdx = Integer.parseInt(request.getParameter("idx")); 
        QnaDAO qnaDAO = new QnaDAO();
        
        try {
        	QnaDTO qnaDTO = qnaDAO.selectQnaDetail(qnaIdx); 
            
            if (qnaDTO != null) {
                request.setAttribute("qnaDTO", qnaDTO); 
                request.getRequestDispatcher("WEB-INF/common/inquiy/modify.jsp").forward(request, response); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
