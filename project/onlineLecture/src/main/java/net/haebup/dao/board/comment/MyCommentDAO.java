package net.haebup.dao.board.comment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.board.comment.BoardCommentDTO;
import net.haebup.dto.board.comment.JoinCommentDTO;
import net.haebup.dto.qna.QnaDTO;
import net.haebup.dto.qna.qnaComment.QnaCommentDTO;
import net.haebup.utils.DatabaseUtil.DBConnPool;
import net.haebup.utils.DatabaseUtil.DbQueryUtil;

public class MyCommentDAO {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	 // 댓글 전체 리스트
//    public List<MyCommentDTO> getMyComments(String userId, int limit, int offset) {
//        List<MyCommentDTO> comments = new ArrayList<>();
//        String sql = "SELECT comment_idx AS myCommentIdx, user_id AS myCommentWriter, comment_content AS myCommentContent, " +
//                     "comment_regdate AS myCommentRegdate, 'comment' AS myCommentType " +
//                     "FROM tbl_comment WHERE user_id = ? " +
//                     "UNION ALL " +
//                     "SELECT qna_comment_idx AS myCommentIdx, qna_comment_writer AS myCommentWriter, qna_comment_content AS myCommentContent, " +
//                     "qna_comment_regdate AS myCommentRegdate, 'qna_comment' AS myCommentType " +
//                     "FROM tbl_qna_comment WHERE qna_comment_writer = ? " +
//                     "ORDER BY myCommentRegdate DESC " +
//                     "LIMIT ? OFFSET ?"; 
//
//        try (Connection conn = DBConnPool.getConnection();
//             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{userId, userId, limit, offset})) {
//            
//            ResultSet rs = dbUtil.executeQuery(); 
//            while (rs.next()) {
//                MyCommentDTO comment = new MyCommentDTO();
//                comment.setMyCommentIdx(rs.getInt("myCommentIdx"));
//                comment.setMyCommentWriter(rs.getString("myCommentWriter"));
//                comment.setMyCommentContent(rs.getString("myCommentContent"));
//                comment.setMyCommentRegdate(rs.getString("myCommentRegdate"));
//                comment.setMyCommentType(rs.getString("myCommentType"));
//                comments.add(comment);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return comments;
//    }
//    
	public List<JoinCommentDTO> getCommentList(String userId, int limit, int offset) throws SQLException {
	    List<JoinCommentDTO> myCommentList = new ArrayList<>();
	    String sql = "SELECT c.comment_idx, c.post_idx, c.user_id, c.comment_content, c.comment_regdate, " +
	                 "q.qna_comment_idx, q.qna_comment_content, q.qna_comment_regdate, q.qna_idx, q.qna_comment_writer, " +
	                 "b.board_idx, b.board_title, b.board_content, b.board_regdate, b.board_type, b.board_writer, b.board_category, " +
	                 "qa.qna_idx AS qna_id, qa.qna_title, qa.qna_content, qa.qna_regdate, qa.qna_writer, qa.qna_type, qa.qna_category " +
	                 "FROM tbl_comment AS c " +
	                 "LEFT JOIN tbl_board AS b ON c.post_idx = b.board_idx " +
	                 "LEFT JOIN tbl_qna_comment AS q ON c.user_id = q.qna_comment_writer " +
	                 "LEFT JOIN tbl_qna AS qa ON q.qna_idx = qa.qna_idx " +
	                 "WHERE c.user_id = ? " +
	                 "ORDER BY c.comment_regdate DESC " +
	                 "LIMIT ? OFFSET ?";

	    try (Connection conn = DBConnPool.getConnection();
	         DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{userId, limit, offset});
	         ResultSet rs = dbUtil.executeQuery()) {

	        while (rs.next()) {
	            myCommentList.add(createJoinCommentDTO(rs));
	        }
	    } catch (SQLException e) {
	        throw new SQLException("유저 ID로 댓글 및 원본 게시글을 조회하는 중 오류가 발생했습니다: " + e.getMessage());
	    }

	    return myCommentList;
	}

	private JoinCommentDTO createJoinCommentDTO(ResultSet rs) throws SQLException {
	    BoardCommentDTO boardComment = new BoardCommentDTO();
	    boardComment.setCommentIdx(rs.getInt("comment_idx"));
	    boardComment.setPostIdx(rs.getInt("post_idx"));
	    boardComment.setUserId(rs.getString("user_id"));
	    boardComment.setCommentContent(rs.getString("comment_content"));
	    boardComment.setCommentRegdate(rs.getTimestamp("comment_regdate") != null ?
	                                   dateFormat.format(rs.getTimestamp("comment_regdate")) : "N/A");

	    QnaCommentDTO qnaComment = new QnaCommentDTO();
	    qnaComment.setQnaCommentIdx(rs.getInt("qna_comment_idx"));
	    qnaComment.setQnaCommentContent(rs.getString("qna_comment_content"));
	    qnaComment.setQnaCommentRegdate(rs.getTimestamp("qna_comment_regdate") != null ?
	                                    dateFormat.format(rs.getTimestamp("qna_comment_regdate")) : "N/A");
	    qnaComment.setQnaIdx(rs.getInt("qna_idx"));
	    qnaComment.setQnaCommentWriter(rs.getString("qna_comment_writer"));

	    BoardDTO board = new BoardDTO();
	    board.setBoardIdx(rs.getInt("board_idx"));
	    board.setBoardTitle(rs.getString("board_title"));
	    board.setBoardContent(rs.getString("board_content"));
	    board.setBoardRegdate(rs.getTimestamp("board_regdate") != null ?
	                          dateFormat.format(rs.getTimestamp("board_regdate")) : "N/A");
	    board.setBoardType(rs.getString("board_type"));
	    board.setBoardWriter(rs.getString("board_writer"));
	    board.setBoardCategory(rs.getString("board_category"));

	    QnaDTO qna = new QnaDTO();
	    qna.setQnaIdx(rs.getInt("qna_id"));
	    qna.setQnaTitle(rs.getString("qna_title"));
	    qna.setQnaContent(rs.getString("qna_content"));
	    qna.setQnaRegdate(rs.getTimestamp("qna_regdate") != null ?
	                      dateFormat.format(rs.getTimestamp("qna_regdate")) : "N/A");
	    qna.setQnaWriter(rs.getString("qna_writer"));
	    qna.setQnaType(rs.getString("qna_type"));
	    qna.setQnaCategory(rs.getString("qna_category"));

	    return new JoinCommentDTO(boardComment, qnaComment, board, qna);
	}
    
    // 페이징
    public List<JoinCommentDTO> getTotalComment(String userId, int pageNo, int pageSize) throws SQLException {
        int limit = pageSize;                          
        int offset = (pageNo - 1) * pageSize;          
        return getCommentList(userId, limit, offset);
    }
    
    // 전체 댓글 수
    public int getTotalCommentsCount(String userId) {
        int count = 0;
        String sql = "SELECT COUNT(*) " +
                     "FROM tbl_comment AS c " +
                     "LEFT JOIN tbl_board AS b ON c.post_idx = b.board_idx " +
                     "LEFT JOIN tbl_qna_comment AS q ON c.user_id = q.qna_comment_writer " +
                     "LEFT JOIN tbl_qna AS qa ON q.qna_idx = qa.qna_idx " +
                     "WHERE c.user_id = ?";

        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{userId});
             ResultSet rs = dbUtil.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
