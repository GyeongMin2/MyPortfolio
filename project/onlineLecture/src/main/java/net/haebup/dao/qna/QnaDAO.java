package net.haebup.dao.qna;
import java.util.List;
import net.haebup.utils.DatabaseUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.ArrayList;

import net.haebup.dto.qna.QnaDTO;

//QnA 유형 G : 일반 QnA, T : 선생님 QnA
public class QnaDAO{
	
	  // 검색 조건이 포함된 질문 목록 조회 메서드
    public List<QnaDTO> selectQnaListByType(int limit, int offset, String qnaType, String qnaCategory, String searchType, String searchKeyword) throws SQLException {
        String sql = "SELECT * FROM tbl_qna WHERE qna_type = ?";
        
        if (qnaCategory != null) {
            sql += " AND qna_category = ?";
        }

        if ("title".equals(searchType)) {
            sql += " AND qna_title LIKE ?";
        } else if ("writer".equals(searchType)) {
            sql += " AND qna_writer LIKE ?";
        } else if ("title_content".equals(searchType)) {
            sql += " AND (qna_title LIKE ? OR qna_content LIKE ?)";
        }

        sql += " ORDER BY qna_regdate DESC LIMIT ? OFFSET ?";

        List<QnaDTO> qnaList = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        params.add(qnaType);

        if (qnaCategory != null) {
            params.add(qnaCategory);
        }

        String searchParam = "%" + searchKeyword + "%";
        if ("title".equals(searchType) || "writer".equals(searchType)) {
            params.add(searchParam);
        } else if ("title_content".equals(searchType)) {
            params.add(searchParam);
            params.add(searchParam);
        }

        params.add(limit);
        params.add(offset);

        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, params.toArray())) {

            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                QnaDTO qnaDTO = new QnaDTO();
                qnaDTO.setQnaIdx(rs.getInt("qna_idx"));
                qnaDTO.setQnaType(rs.getString("qna_type"));
                qnaDTO.setQnaCategory(rs.getString("qna_category"));
                qnaDTO.setQnaTitle(rs.getString("qna_title"));
                qnaDTO.setQnaContent(rs.getString("qna_content"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(rs.getTimestamp("qna_regdate"));
                qnaDTO.setQnaRegdate(formattedDate);
                qnaDTO.setQnaWriter(rs.getString("qna_writer"));
                qnaList.add(qnaDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("질문 목록 조회 중 오류가 발생하였습니다." + e);
        }
        return qnaList;
    }

    // 페이징을 위한 메서드
    public List<QnaDTO> getQnaListByPage(int pageNo, int pageSize, String qnaType, String qnaCategory, String searchType, String searchKeyword) throws SQLException {
        int limit = pageSize;
        int offset = (pageNo - 1) * pageSize;
        
        return selectQnaListByType(limit, offset, qnaType, qnaCategory, searchType, searchKeyword);
    }
    

    // 검색 조건이 포함된 전체 QnA 수 조회
    public int getTotalCount(String qnaType, String qnaCategory, String searchType, String searchKeyword) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_qna WHERE qna_type = ?";
        
        if (qnaCategory != null) {
            sql += " AND qna_category = ?";
        }

        if ("title".equals(searchType)) {
            sql += " AND qna_title LIKE ?";
        } else if ("writer".equals(searchType)) {
            sql += " AND qna_writer LIKE ?";
        } else if ("title_content".equals(searchType)) {
            sql += " AND (qna_title LIKE ? OR qna_content LIKE ?)";
        }

        List<Object> params = new ArrayList<>();
        params.add(qnaType);

        if (qnaCategory != null) {
            params.add(qnaCategory);
        }

        String searchParam = "%" + searchKeyword + "%";
        if ("title".equals(searchType) || "writer".equals(searchType)) {
            params.add(searchParam);
        } else if ("title_content".equals(searchType)) {
            params.add(searchParam);
            params.add(searchParam);
        }

        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, params.toArray())) {

            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("qna 개수 조회 중 오류가 발생하였습니다." + e);
        }
    }
    
    //선생님의 QNA
    public List<QnaDTO> selectQnaListByTeacher(int limit, int offset, String teacherId) throws SQLException {
        String sql = "SELECT q.* FROM tbl_qna q JOIN tbl_lecture l ON q.qna_category = l.lecture_code OR q.qna_category = l.teacher_id WHERE l.teacher_id = ?";
        
        sql += " ORDER BY q.qna_regdate DESC LIMIT ? OFFSET ?";

        List<QnaDTO> qnaList = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        params.add(teacherId);
        params.add(limit);
        params.add(offset);

        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, params.toArray())) {

            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                QnaDTO qnaDTO = new QnaDTO();
                qnaDTO.setQnaIdx(rs.getInt("qna_idx"));
                qnaDTO.setQnaType(rs.getString("qna_type"));
                qnaDTO.setQnaCategory(rs.getString("qna_category"));
                qnaDTO.setQnaTitle(rs.getString("qna_title"));
                qnaDTO.setQnaContent(rs.getString("qna_content"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(rs.getTimestamp("qna_regdate"));
                qnaDTO.setQnaRegdate(formattedDate);
                qnaDTO.setQnaWriter(rs.getString("qna_writer"));
                qnaList.add(qnaDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("강의에 따른 질문 목록 조회 중 오류가 발생하였습니다." + e);
        }
        return qnaList;
    }
    //선생님 qna갯수
    public int getTotalCountByTeacher(String teacherId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_qna q " +
                     "JOIN tbl_lecture l ON q.qna_category = l.lecture_code " +
                     "WHERE l.teacher_id = ?";

        try (Connection conn = DBConnPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, teacherId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1); // 첫 번째 열에서 COUNT 값을 가져옵니다.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("선생님 QnA 총 개수 조회 중 오류가 발생했습니다." + e);
        }
        return 0;
    }

    //질문 등록
    //qna_type : G, T
    //qna_category : 강의 코드 또는 null가능(1대1 질문일경우 질문타입 가능)
    //null 일 경우 일반 QnA (G)
//    public int insertQna(QnaDTO qnaDTO) throws SQLException{
//        String sql = "INSERT INTO tbl_qna (qna_type,qna_category, qna_title, qna_content, qna_writer) VALUES (?, ?, ?, ?, ?)";
//        int result = 0;
//        try(Connection conn = DBConnPool.getConnection();
//            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaDTO.getQnaType(), qnaDTO.getQnaCategory(), qnaDTO.getQnaTitle(), qnaDTO.getQnaContent(), qnaDTO.getQnaWriter()})){
//                result = dbUtil.executeUpdate();
//        }catch(SQLException e){
//            e.printStackTrace();
//            throw new RuntimeException("질문 등록 중 오류가 발생하였습니다."+e);
//        }
//        return result;
//    }
    public int insertQna(QnaDTO qnaDTO) throws SQLException{
    	String sql = "INSERT INTO tbl_qna (qna_type, qna_category, qna_title, qna_content, qna_writer) VALUES (?, ?, ?, ?, ?)";
    	int qnaIdx = 0;
    	try(Connection conn = DBConnPool.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    		pstm.setString(1, qnaDTO.getQnaType());
    		pstm.setString(2, qnaDTO.getQnaCategory());
    		pstm.setString(3, qnaDTO.getQnaTitle());
    		pstm.setString(4, qnaDTO.getQnaContent());
    		pstm.setString(5, qnaDTO.getQnaWriter());
    		
    		 int row = pstm.executeUpdate();
             
             if (row > 0) {
                 ResultSet rs = pstm.getGeneratedKeys();
                 if (rs.next()) {
                	 qnaIdx = rs.getInt(1);  
                 }
             }
    	
    	}catch(SQLException e){
    		e.printStackTrace();
    		throw new RuntimeException("질문 등록 중 오류가 발생하였습니다."+e);
    	}
    	return qnaIdx;
    }

    public int updateQna(QnaDTO qnaDTO) throws SQLException{
        String sql = "UPDATE tbl_qna SET qna_title = ?, qna_content = ? WHERE qna_idx = ?";
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaDTO.getQnaTitle(), qnaDTO.getQnaContent(), qnaDTO.getQnaIdx()})){
        	return dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("질문 수정 중 오류가 발생하였습니다."+e);
        }
    }

    //질문 삭제
    public int deleteQna(int qnaIdx) throws SQLException{
    	 String deleteCommentsSql = "DELETE FROM tbl_Qna_comment WHERE qna_idx = ?";
 	    String deleteQnaSql = "DELETE FROM tbl_qna WHERE qna_idx = ?";
 	   try (Connection conn = DBConnPool.getConnection()) {
	        conn.setAutoCommit(false); 

	        try (DbQueryUtil commentUtil = new DbQueryUtil(conn, deleteCommentsSql, new Object[]{qnaIdx})) {
	            commentUtil.executeUpdate();
	        }

	        try (DbQueryUtil qnaUtil = new DbQueryUtil(conn, deleteQnaSql, new Object[]{qnaIdx})) {
	            int result = qnaUtil.executeUpdate();
	            conn.commit(); 
	            return result;
	        } catch (SQLException e) {
	            conn.rollback();  
	            throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다. " + e);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다. " + e);
	    }
    }

    //질문 상세 조회
    public QnaDTO selectQnaDetail(int qnaIdx) throws SQLException{
        String sql = "SELECT * FROM tbl_qna WHERE qna_idx = ?";
        QnaDTO qnaDTO = null;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{qnaIdx})){
                ResultSet rs = dbUtil.executeQuery();
                if(rs.next()){
                    qnaDTO = new QnaDTO();
                    qnaDTO.setQnaIdx(rs.getInt("qna_idx"));
                    qnaDTO.setQnaType(rs.getString("qna_type"));
                    qnaDTO.setQnaCategory(rs.getString("qna_category"));
                    qnaDTO.setQnaTitle(rs.getString("qna_title"));
                    qnaDTO.setQnaContent(rs.getString("qna_content"));
                    // 날짜 변환
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(rs.getTimestamp("qna_regdate"));
                    qnaDTO.setQnaRegdate(formattedDate);
                    qnaDTO.setQnaWriter(rs.getString("qna_writer"));
                    qnaDTO.setQnaCategory(rs.getString("qna_category"));
                }
            }
        return qnaDTO;
    }
    
}
