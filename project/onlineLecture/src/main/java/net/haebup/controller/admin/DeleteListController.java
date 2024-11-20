package net.haebup.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.BoardDAO;
import net.haebup.dto.member.MemberDTO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/deleteList.do")
public class DeleteListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 관리자 권한 체크
        MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
        if (user == null || !"A".equals(user.getUserType())) {
            response.getWriter().print("<script>alert('관리자 권한이 필요합니다.'); location.href='/';</script>");
            return;
        }

        String idxList = request.getParameter("idx");
        if (idxList == null || idxList.trim().isEmpty()) {
            response.getWriter().print("<script>alert('삭제할 게시글을 선택해주세요.'); location.href='javascript:history.back();';</script>");
            return;
        }

        BoardDAO boardDAO = new BoardDAO();
        String[] idxArray = idxList.split(",");
        int successCount = 0;

        try {
            for (String idx : idxArray) {
                int boardIdx = Integer.parseInt(idx.trim());
                int result = boardDAO.deleteByBoardIdx(boardIdx);
                if (result > 0) {
                    successCount++;
                }
            }

            response.setContentType("text/html; charset=UTF-8");
            if (successCount == idxArray.length) {
                response.getWriter().print("<script>alert('선택한 게시글이 모두 삭제되었습니다.'); location.href='/admin/gotoTotalPostList.do';</script>");
            } else {
                response.getWriter().print("<script>alert('" + successCount + "개의 게시글이 삭제되었습니다.'); location.href='/admin/gotoTotalPostList.do';</script>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("<script>alert('게시글 삭제 중 오류가 발생했습니다.'); location.href='/admin/gotoTotalPostList.do';</script>");
        }
    }
}