package net.haebup.dao.qna.qnaComment;
import java.util.List;
import net.haebup.utils.DatabaseUtil.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.ArrayList;
import net.haebup.dto.qna.qnaComment.QnaCommentDTO;

/**
 * QnA 댓글 관련 데이터베이스 작업을 처리하는 DAO 클래스
 */
public class QnaCommentDAO {

    /**
     * 특정 QnA에 대한 댓글 목록을 조회한다.
     * @param qnaIdx QnA 인덱스
     * @return 댓글 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<QnaCommentDTO> selectQnaCommentList(int qnaIdx) throws SQLException{
        String sql = "SELECT * FROM tbl_qna_comment WHERE qna_idx = ?";
        List<QnaCommentDTO> qnaCommentList = new ArrayList<>(); 
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaIdx})){
                ResultSet rs = dbUtil.executeQuery();
                while(rs.next()){
                    QnaCommentDTO qnaCommentDTO = new QnaCommentDTO();
                    qnaCommentDTO.setQnaCommentIdx(rs.getInt("qna_comment_idx"));
                    qnaCommentDTO.setQnaIdx(rs.getInt("qna_idx"));
                    qnaCommentDTO.setQnaCommentContent(rs.getString("qna_comment_content"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(rs.getTimestamp("qna_comment_regdate"));
                    qnaCommentDTO.setQnaCommentRegdate(formattedDate);
                    qnaCommentDTO.setQnaCommentWriter(rs.getString("qna_comment_writer"));
                    qnaCommentList.add(qnaCommentDTO);
                }
            }   
        return qnaCommentList;
    }

    /**
     * 댓글 개수 조회
     * @param qnaIdx
     * @return
     * @throws SQLException
     */
    public int selectQnaCommentCount(int qnaIdx) throws SQLException{
        String sql = "SELECT COUNT(*) FROM tbl_qna_comment WHERE qna_idx = ?";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaIdx})){
                ResultSet rs = dbUtil.executeQuery();
                if(rs.next()){
                    result = rs.getInt(1);
                }
            }
        return result;
    }

    /**
     * 새로운 QnA 댓글을 등록한다.
     * @param qnaCommentDTO 등록할 댓글 정보
     * @return 등록된 댓글 수 (성공 시 1, 실패 시 0)
     * @throws SQLException SQL 예외 발생 시
     */
    public int insertQnaComment(QnaCommentDTO qnaCommentDTO) throws SQLException{
        String sql = "INSERT INTO tbl_qna_comment (qna_idx, qna_comment_content, qna_comment_writer) VALUES (?, ?, ?)";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaCommentDTO.getQnaIdx(), qnaCommentDTO.getQnaCommentContent(), qnaCommentDTO.getQnaCommentWriter()})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 등록 중 오류가 발생하였습니다."+e);
        }
        return result;
    }

    /**
     * 기존 QnA 댓글을 수정한다.
     * @param qnaCommentDTO 수정할 댓글 정보
     * @return 수정된 댓글 수 (성공 시 1, 실패 시 0)
     * @throws SQLException SQL 예외 발생 시
     */
    public int updateQnaComment(QnaCommentDTO qnaCommentDTO) throws SQLException{
        String sql = "UPDATE tbl_qna_comment SET qna_comment_content = ? WHERE qna_comment_idx = ?";
        int result = 0; 
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaCommentDTO.getQnaCommentContent(), qnaCommentDTO.getQnaCommentIdx()})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 수정 중 오류가 발생하였습니다."+e);
        }
        return result;
    }

    /**
     * QnA 댓글을 삭제한다.
     * @param qnaCommentIdx 삭제할 댓글의 인덱스
     * @return 삭제된 댓글 수 (성공 시 1, 실패 시 0)
     * @throws SQLException SQL 예외 발생 시
     */
    public int deleteQnaComment(int qnaCommentIdx) throws SQLException{
        String sql = "DELETE FROM tbl_qna_comment WHERE qna_comment_idx = ?";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaCommentIdx})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("댓글 삭제 중 오류가 발생하였습니다."+e);
        }
        return result;
    }
}
