package net.haebup.dao.board.comment;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import net.haebup.dto.board.comment.BoardCommentDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.haebup.utils.DatabaseUtil.*;

public class CommentDAO {

    // 댓글 목록 조회
    public List<BoardCommentDTO> selectCommentList(int boardIdx) throws SQLException{
        String sql = "SELECT * FROM tbl_comment WHERE post_idx = ? ORDER BY comment_idx ASC";
        List<BoardCommentDTO> commentList = new ArrayList<>();
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardIdx})){
                ResultSet rs = dbUtil.executeQuery();
                while(rs.next()){
                    BoardCommentDTO commentDTO = new BoardCommentDTO();
                    commentDTO.setCommentIdx(rs.getInt("comment_idx"));
                    commentDTO.setPostIdx(rs.getInt("post_idx"));
                    commentDTO.setCommentContent(rs.getString("comment_content"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(rs.getTimestamp("comment_regdate"));
                    commentDTO.setCommentRegdate(formattedDate);
                    
                    commentDTO.setUserId(rs.getString("user_id"));
                    commentList.add(commentDTO);
                }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 목록 조회 중 오류가 발생하였습니다."+e);
        }
        return commentList;
    }
    
    //댓글조회
    public BoardCommentDTO selectCommentIdx(int commentIdx) throws SQLException {
        String sql = "SELECT * FROM tbl_comment WHERE comment_idx = ?";
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{commentIdx})) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                BoardCommentDTO commentDTO = new BoardCommentDTO();
                commentDTO.setCommentIdx(rs.getInt("comment_idx"));
                commentDTO.setPostIdx(rs.getInt("post_idx"));
                commentDTO.setUserId(rs.getString("user_id"));
                commentDTO.setCommentContent(rs.getString("comment_content"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(rs.getTimestamp("comment_regdate"));
                commentDTO.setCommentRegdate(formattedDate);
                return commentDTO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("댓글 조회 중 오류가 발생하였습니다." + e);
        }
        return null;
    }
    
    //전체 댓글 갯수
    public int getCount(int boardIdx) throws SQLException{
        String sql = "SELECT COUNT(*) FROM tbl_comment WHERE post_idx = ?";
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardIdx})){
                ResultSet rs = dbUtil.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
                return 0;
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 개수 조회 중 오류가 발생하였습니다."+e);
        }
    }
    

    public int insertComment(BoardCommentDTO commentDTO) throws SQLException{
        String sql = "INSERT INTO tbl_comment (post_idx, comment_content, user_id) VALUES (?, ?, ?)";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{commentDTO.getPostIdx(), commentDTO.getCommentContent(), commentDTO.getUserId()})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 등록 중 오류가 발생하였습니다."+e);
        }
        return result;
    }
    
    //수정
     public int updateComment(BoardCommentDTO commentDTO) throws SQLException{
         String sql = "UPDATE tbl_comment SET comment_content = ? WHERE comment_idx = ?";
         int result = 0;
         try(Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{commentDTO.getCommentContent(), commentDTO.getCommentIdx()})){
                 result = dbUtil.executeUpdate();
         }catch(SQLException e){
             e.printStackTrace();
             throw new RuntimeException("댓글 수정 중 오류가 발생하였습니다."+e);
         }
         return result;
     }
     
     //삭제
    public int deleteComment(int commentIdx) throws SQLException{
        String sql = "DELETE FROM tbl_comment WHERE comment_idx = ?";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{commentIdx})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 삭제 중 오류가 발생하였습니다."+e);
        }
        return result;
    }

}