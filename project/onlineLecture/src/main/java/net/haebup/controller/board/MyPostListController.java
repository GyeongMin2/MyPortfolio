package net.haebup.controller.board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.haebup.dao.board.MyPostDAO;
import net.haebup.dao.board.comment.MyCommentDAO;
import net.haebup.dao.lecture.LectureDAO;
import net.haebup.dao.member.payment.PaymentDAO;
import net.haebup.dto.board.MyPostDTO;
import net.haebup.dto.board.comment.JoinCommentDTO;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.member.MemberDTO;
import net.haebup.dto.member.payment.PaymentDTO;
import net.haebup.utils.PaginationUtil.Pagination;
import net.haebup.dto.member.MemberDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/myStudyRoom/common/gotoWriteDetail.do")
public class MyPostListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("내가쓴글 댓글 확인 서블릿 시작");
        MemberDTO user = (MemberDTO) request.getSession().getAttribute("user");
        String writer = user.getUserId();

        // 게시글 관련 파라미터
        String postPageNoParam = request.getParameter("postPageNo");
        String postPageSizeParam = request.getParameter("postPageSize");
        int postPageNo = postPageNoParam != null ? Integer.parseInt(postPageNoParam) : 1;
        int postPageSize = postPageSizeParam != null ? Integer.parseInt(postPageSizeParam) : 5;

        // 댓글 관련 파라미터
        String commentPageNoParam = request.getParameter("commentPageNo");
        String commentPageSizeParam = request.getParameter("commentPageSize");
        int commentPageNo = commentPageNoParam != null ? Integer.parseInt(commentPageNoParam) : 1;
        int commentPageSize = commentPageSizeParam != null ? Integer.parseInt(commentPageSizeParam) : 5;

        try {
            MyPostDAO postDAO = new MyPostDAO();
            MyCommentDAO commentDAO = new MyCommentDAO();

            // 게시글 페이징 처리
            int totalPostCount = postDAO.getTotalPost(writer, writer);
            List<MyPostDTO> postList = postDAO.getPostsByWriter(postPageSize, (postPageNo - 1) * postPageSize, writer,
                    writer);
//            for(MyPostDTO postDTO : postList) {
//            	System.out.println("게시글 제목확인 : "+ postDTO.getPostTitle());
//            }
            Pagination postPagination = new Pagination(postPageNo, postPageSize, totalPostCount, 10);

            // 댓글 페이징 처리
            int totalCommentCount = commentDAO.getTotalCommentsCount(writer);
            List<JoinCommentDTO> commentList = commentDAO.getCommentList(writer, commentPageSize,
                    (commentPageNo - 1) * commentPageSize);
            Pagination commentPagination = new Pagination(commentPageNo, commentPageSize, totalCommentCount, 10);

            LectureDAO lectureDAO = new LectureDAO();
            List<LectureDTO> lectureList = null;
            int lectureTotalCount = 0;
            List<PaymentDTO> paymentList = null;
            try {
                lectureList = lectureDAO.getLectureListByUserId(user.getUserId(), true);
                PaymentDAO paymentDAO = new PaymentDAO();
                paymentList = paymentDAO.getPaymentListAll(user.getUserId());
                lectureTotalCount = lectureDAO.getLectureTotalCountByUserId(user.getUserId());
            } catch (SQLException e) {
                System.out.println("SQL 예외 발생: " + e.getMessage());
                request.setAttribute("message", "강의 목록 조회 중 오류가 발생하였습니다.");
                response.sendRedirect(request.getHeader("Referer"));
                return;
            }

            request.setAttribute("lectureList", lectureList);
            request.setAttribute("lectureTotalCount", lectureTotalCount);
            System.out.println(lectureTotalCount);
            request.setAttribute("paymentList", paymentList);
            request.setAttribute("postList", postList);
            System.out.println(postList);
            request.setAttribute("postPagination", postPagination);
            request.setAttribute("commentList", commentList);
            request.setAttribute("commentPagination", commentPagination);
            request.setAttribute("writer", writer);

            request.getRequestDispatcher("/WEB-INF/common/myPage/myPage.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}