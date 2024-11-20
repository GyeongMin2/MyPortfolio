package net.haebup.dao.board;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.haebup.dto.board.BoardDTO;
import net.haebup.dto.board.MyPostDTO;
import net.haebup.utils.DatabaseUtil.DBConnPool;
import net.haebup.utils.DatabaseUtil.DbQueryUtil;

public class MyPostDAO {
	
	public List<MyPostDTO> getPostsByWriter(int limit, int offset, String qnaWriter, String boardWriter) {
	    List<MyPostDTO> postList = new ArrayList<>();
//	    System.out.println("내가쓴글리스트조회 메서드 시작");
	    String sql = "SELECT * FROM (" +
	                 "SELECT board_type AS post_type, board_idx AS post_idx, board_writer AS post_writer, " +
	                 "board_title AS post_title, board_regdate AS post_regdate, board_content AS post_content, " +
	                 "NULL AS post_category " +
	                 "FROM tbl_board WHERE board_writer = ? " +
	                 "UNION ALL " +
	                 "SELECT qna_type AS post_type, qna_idx AS post_idx, qna_writer AS post_writer, " +
	                 "qna_title AS post_title, qna_regdate AS post_regdate, qna_content AS post_content, " +
	                 "qna_category AS post_category " +
	                 "FROM tbl_qna WHERE qna_writer = ? " +
	                 ") AS combined_posts " +
	                 "ORDER BY post_regdate DESC " +
	                 "LIMIT ? OFFSET ?"; 
	    
	    try (Connection conn = DBConnPool.getConnection();
	         DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardWriter, qnaWriter, limit, offset})) {
	         
	        ResultSet rs = dbUtil.executeQuery();
	        while (rs.next()) {
	        	MyPostDTO postDTO = new MyPostDTO();
	            postDTO.setPostIdx(rs.getInt("post_idx"));
	            postDTO.setPostType(rs.getString("post_type"));
	            postDTO.setPostTitle(rs.getString("post_title"));
	            postDTO.setPostContent(rs.getString("post_content"));
	            postDTO.setPostWriter(rs.getString("post_writer"));
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(rs.getTimestamp("post_regdate"));
                postDTO.setPostRegdate(formattedDate);
	            postDTO.setPostCategory(rs.getString("post_category"));
	            postList.add(postDTO);
	        }
//	        System.out.println("글 확인 리스트: "+postList);
	        return postList;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("내가 쓴 글 조회 중 오류가 발생하였습니다." + e);
	        
	    }
	}
 
    public List<MyPostDTO> getPostdListByPage(int pageNo, int pageSize, String qnaWriter, String boardWriter) throws SQLException {
        int limit = pageSize;                          
        int offset = (pageNo - 1) * pageSize;          

        return getPostsByWriter(limit, offset, qnaWriter, boardWriter);
    }
    
    public int getTotalPost(String qnaWriter, String boardWriter) {
        String sql = "SELECT ( " +
                     "SELECT COUNT(*) FROM tbl_board WHERE board_writer = ? " +
                     ") + ( " +
                     "SELECT COUNT(*) FROM tbl_qna WHERE qna_writer = ? " +
                     ") AS total_count";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardWriter, qnaWriter})) {
            
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_count");
            } else {
                return 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 개수 조회 중 오류가 발생하였습니다." + e);
        }
    }
}
 
