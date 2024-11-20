package net.haebup.controller.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dao.board.file.FileDAO;
import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.board.file.FileDTO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.utils.fileUtil.FileIOUtil;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/postWrite.do")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 100)    // 100MB
public class PostWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostWriteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
		String userType = user.getUserType();
		String boardType = request.getParameter("type");
		String category = request.getParameter("category");
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardType(boardType);
		boardDTO.setBoardTitle(request.getParameter("boardTitle"));
		boardDTO.setBoardContent(request.getParameter("boardContent"));
		boardDTO.setBoardWriter(request.getParameter("boardWriter"));
		
		// 강의 관련 게시글일 때만 카테고리 설정
		if ("C".equals(boardType) || "R".equals(boardType)) {
			boardDTO.setBoardCategory(category);
		} else {
			boardDTO.setBoardCategory(null);
		}

		BoardDAO boardDAO = new BoardDAO();
		int boardIdx = 0;

		try {
			boardIdx = boardDAO.insertBoard(boardDTO);
			
			if (boardIdx > 0) {
				Part filePart = request.getPart("file");
				if (filePart != null && filePart.getSize() > 0) {
					try {
						String filePath = FileIOUtil.uploadBoardAttachment(request, "file");
						if (filePath != null) {
							FileDTO fileDTO = new FileDTO();
							fileDTO.setBoardIdx(boardIdx);
							fileDTO.setFileName(filePart.getSubmittedFileName());
							fileDTO.setFilePath(filePath);
							fileDTO.setFileSize((int) filePart.getSize());

							FileDAO fileDAO = new FileDAO();
							int fileResult = fileDAO.insertFile(fileDTO);
							
							if (fileResult <= 0) {
								System.out.println("파일 정보 DB 저장 실패");
							}
						}
					} catch (Exception e) {
						System.out.println("파일 업로드 실패: " + e.getMessage());
						e.printStackTrace();
					}
				}
				
				request.setAttribute("msg", "게시글 작성 성공했습니다.");
				String url = "/gotoPostList.do?type=" + boardType;
				if (category != null && !category.isEmpty()) {
					url += "&category=" + category;
				}
				request.setAttribute("url", url);
				request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp")
					   .forward(request, response);
			} else {
				request.setAttribute("msg", "게시글이 작성되지 않았습니다.");
				request.setAttribute("url", "javascript:history.back();");
				request.getRequestDispatcher("/WEB-INF/common/commonArea/successAlert.jsp")
					   .forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다.");
		}
	}
}
