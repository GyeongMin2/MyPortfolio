
package net.haebup.controller.qna;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dao.board.file.FileDAO;
import net.haebup.dao.qna.QnaDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.board.file.FileDTO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.qna.QnaDTO;
import net.haebup.utils.fileUtil.FileIOUtil;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/qnaWrite.do")
public class QnaWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
        String category = request.getParameter("category");
        String teacherId = request.getParameter("teacherId");
		String qnaType = request.getParameter("type");
		
		QnaDTO qnaDTO = new QnaDTO();
		qnaDTO.setQnaType(qnaType);  
		qnaDTO.setQnaTitle(request.getParameter("qnaTitle"));
		qnaDTO.setQnaContent(request.getParameter("qnaContent"));
		qnaDTO.setQnaWriter(request.getParameter("qnaWriter"));
		qnaDTO.setQnaCategory((category == null || category.isEmpty()) ? teacherId :category);


        QnaDAO qndDAO = new QnaDAO();
        int result = 0;
        try {
        	result = qndDAO.insertQna(qnaDTO);
        	
        	if(result>0) {
        		request.setAttribute("msg", "qna 등록 성공.");
        		if(teacherId != null) {
        			request.setAttribute("url", "/gotoQnaList.do?type=" + qnaType + "teacherId="+teacherId);
        		}else if(category != null) {
        			request.setAttribute("url", "/gotoQnaList.do?type=" + qnaType + "category="+category);
        		}
                request.setAttribute("url", "/gotoQnaList.do?type=" + qnaType);
                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
        	}else {
        		request.setAttribute("msg", "qna 등록실패");
                request.setAttribute("url", "/gotoQnaList.do?type=" + qnaType);
                request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp").forward(request, response);
        	}
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("<script>alert('오류 발생: " + e.getMessage() + "'); location.href='javascript:history.back();';</script>");
        }
	}
	
}
