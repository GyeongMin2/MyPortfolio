package net.haebup.dao.board;
import java.util.List;
import net.haebup.dto.board.BoardDTO;
import net.haebup.utils.DatabaseUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class BoardDAO implements IFBoardDAO{
 // 게시물 목록 조회 : 게시물 타입에 따른 게시물 목록 조회 p = 자유게시판, n = 공지사항, d : 자료실, c:강의공지 특정강의 공지, 자료실 이동가능
	@Override
	public List<BoardDTO> getBoardList(int limit, int offset, String boardType, String boardCategory, String boardWriter, String searchType, String searchKeyword) throws SQLException {
	    StringBuilder sql = new StringBuilder("SELECT * FROM tbl_board WHERE board_type = ?");

	    List<Object> params = new ArrayList<>();
	    params.add(boardType);

	    // 카테고리 조건 추가
	    if (boardCategory != null && !boardCategory.isEmpty()) {
	        sql.append(" AND board_category = ?");
	        params.add(boardCategory);
	    }

	    // 작성자 조건 추가
	    if (boardWriter != null && !boardWriter.isEmpty()) {
	        sql.append(" AND board_writer = ?");
	        params.add(boardWriter);
	    }

	    // 검색 조건 추가
	    if (searchType != null && searchKeyword != null && !searchKeyword.isEmpty()) {
	        switch (searchType) {
	            case "title":
	                sql.append(" AND board_title LIKE ?");
	                params.add("%" + searchKeyword + "%");
	                break;
	            case "content":
	                sql.append(" AND board_content LIKE ?");
	                params.add("%" + searchKeyword + "%");
	                break;
	            case "title_content":
	                sql.append(" AND (board_title LIKE ? OR board_content LIKE ?)");
	                String searchParam = "%" + searchKeyword + "%";
	                params.add(searchParam);
	                params.add(searchParam);
	                break;
	            default:
	                throw new IllegalArgumentException("유효하지 않은 검색 타입입니다.");
	        }
	    }

	    sql.append(" ORDER BY board_regdate DESC LIMIT ? OFFSET ?");
	    params.add(limit);
	    params.add(offset);
	    
//	    System.out.println("SQL Query: " + sql.toString());
//	    System.out.println("Parameters: " + params);
	    // DB 연결 및 쿼리 실행
	    try (Connection conn = DBConnPool.getConnection();
	         DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params.toArray())) {
	        ResultSet rs = dbUtil.executeQuery();
	        List<BoardDTO> boardList = new ArrayList<>();

	        while (rs.next()) {
	            BoardDTO boardDTO = new BoardDTO();
	            boardDTO.setBoardIdx(rs.getInt("board_idx"));
	            boardDTO.setBoardType(rs.getString("board_type"));
	            boardDTO.setBoardCategory(rs.getString("board_category"));
	            boardDTO.setBoardTitle(rs.getString("board_title"));
	            boardDTO.setBoardWriter(rs.getString("board_writer"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(rs.getTimestamp("board_regdate"));
                boardDTO.setBoardRegdate(formattedDate);

	            boardList.add(boardDTO);
	        }
	        return boardList;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("게시물 조회 중 오류가 발생하였습니다." + e);
	    }
	}

	public List<BoardDTO> getBoardListByPage(int pageNo, int pageSize, String boardType, String boardCategory, String boardWriter, String searchType, String searchKeyword) throws SQLException {
	    int limit = pageSize;                          
	    int offset = (pageNo - 1) * pageSize;          

	    return getBoardList(limit, offset, boardType, boardCategory, boardWriter, searchType, searchKeyword);
	}
    

    // 내 게시물 목록 조회
    @Override
    public List<BoardDTO> getUserBoardList(int limit, int offset, String boardType, String userId) throws SQLException{
        String sql = "SELECT * FROM tbl_board WHERE board_type = ? AND board_writer = ? ORDER BY board_idx DESC LIMIT ? OFFSET ?";
        List<BoardDTO> boardList = new ArrayList<>();
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardType,userId,limit,offset})){
                ResultSet rs = dbUtil.executeQuery();
                while(rs.next()){
                    BoardDTO boardDTO = new BoardDTO();
                    boardDTO.setBoardIdx(rs.getInt("board_idx"));
                    boardDTO.setBoardType(rs.getString("board_type"));
                    boardDTO.setBoardTitle(rs.getString("board_title"));
                    boardDTO.setBoardWriter(rs.getString("board_writer"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(rs.getTimestamp("board_regdate"));
                    boardDTO.setBoardRegdate(formattedDate);
                    boardList.add(boardDTO);
                }
                return boardList;
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("게시물 조회 중 오류가 발생하였습니다."+e);
        }
    }
    
    // 게시물 개수 조회
    @Override
    public int getTotalCount(String boardType, String boardCategory, String boardWriter) throws SQLException{
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tbl_board WHERE board_type = ?");
        List<Object> params = new ArrayList<>();
        params.add(boardType);
        
        if (boardCategory != null && !boardCategory.isEmpty()) {
            sql.append(" AND board_category = ?");
            params.add(boardCategory);
        }
        
        if (boardWriter != null && !boardWriter.isEmpty()) {
            sql.append(" AND board_writer = ?");
            params.add(boardWriter);
        }
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params.toArray())) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 개수 조회 중 오류가 발생하였습니다." + e);
        }
    }
    
    public int getTotalCount(String boardType, String boardCategory, String searchType, String searchKeyword, String boardWriter) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tbl_board");
        List<Object> params = new ArrayList<>();

        boolean hasWhere = false;

        // boardType
        if (boardType != null && !boardType.isEmpty()) {
            sql.append(" WHERE board_type = ?");
            params.add(boardType);
            hasWhere = true;
        }

        // boardCategory 
        if (boardCategory != null && !boardCategory.isEmpty()) {
            sql.append(hasWhere ? " AND" : " WHERE").append(" board_category = ?");
            params.add(boardCategory);
            hasWhere = true;
        }

        // boardWriter  (D자료실 C강의공지)
        if (boardWriter != null && !boardWriter.isEmpty()) {
            sql.append(hasWhere ? " AND" : " WHERE").append(" board_writer = ?");
            params.add(boardWriter);
            hasWhere = true;
        }

        // 검색 조건 추가
        if (searchType != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            String searchParam = "%" + searchKeyword + "%";
            if ("title".equals(searchType)) {
                sql.append(hasWhere ? " AND" : " WHERE").append(" board_title LIKE ?");
                params.add(searchParam);
            } else if ("writer".equals(searchType)) {
                sql.append(hasWhere ? " AND" : " WHERE").append(" board_writer LIKE ?");
                params.add(searchParam);
            } else if ("title_content".equals(searchType)) {
                sql.append(hasWhere ? " AND" : " WHERE").append(" (board_title LIKE ? OR board_content LIKE ?)");
                params.add(searchParam);
                params.add(searchParam);
            }
        }

        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params.toArray())) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);  
            }
            return 0;  
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 개수 조회 중 오류가 발생하였습니다: " + e);
        }
    }
    

    // 내 게시물 개수 조회
    @Override
    public int getUserTotalCount(String boardType, String userId) throws SQLException{
        String sql = "SELECT COUNT(*) FROM tbl_board WHERE board_type = ? AND board_writer = ?";
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardType,userId})){
                ResultSet rs = dbUtil.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
                return 0;
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("내 게시물 개수 조회 중 오류가 발생하였습니다."+e);
        }
    }

    // 게시물 상세 조회 리턴 null 이면 존재하지 않는 게시물
    @Override
    public BoardDTO getBoardDetail(int boardIdx) throws SQLException{
        String sql = "SELECT * FROM tbl_board WHERE board_idx = ?";
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardIdx})){
                ResultSet rs = dbUtil.executeQuery();
                if(rs.next()){
                    BoardDTO boardDTO = new BoardDTO();
                    boardDTO.setBoardIdx(rs.getInt("board_idx"));
                    boardDTO.setBoardType(rs.getString("board_type"));
                    boardDTO.setBoardTitle(rs.getString("board_title"));
                    boardDTO.setBoardContent(rs.getString("board_content"));
                    boardDTO.setBoardWriter(rs.getString("board_writer"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(rs.getTimestamp("board_regdate"));
                    boardDTO.setBoardRegdate(formattedDate);
                    return boardDTO;
                }else{
                    return null;
                }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("게시물 상세 조회 중 오류가 발생하였습니다."+e);
        }
    }

    @Override
    public int deleteByBoardIdx(int boardIdx) throws SQLException{
    	 String deleteCommentsSql = "DELETE FROM tbl_comment WHERE post_idx = ?";
    	 String deleteFileSql = "DELETE FROM tbl_file WHERE board_idx = ?";
	     String deleteBoardSql = "DELETE FROM tbl_board WHERE board_idx = ?";

    	    try (Connection conn = DBConnPool.getConnection()) {
    	    	//트랜잭션 시작
    	        conn.setAutoCommit(false); 
    	        // 댓글삭제
    	        try (DbQueryUtil commentUtil = new DbQueryUtil(conn, deleteCommentsSql, new Object[]{boardIdx})) {
    	            commentUtil.executeUpdate();
    	        }
    	        // 파일 삭제
    	        try (DbQueryUtil fileUtil = new DbQueryUtil(conn, deleteFileSql, new Object[]{boardIdx})) {
    	            fileUtil.executeUpdate(); 
    	        }
    	        // 게시글삭제
    	        try (DbQueryUtil boardUtil = new DbQueryUtil(conn, deleteBoardSql, new Object[]{boardIdx})) {
    	            int result = boardUtil.executeUpdate();
    	            // 커밋
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

    @Override
    public int updateBoard(BoardDTO boardDTO) throws SQLException{
        String sql = "UPDATE tbl_board SET board_title = ?, board_content = ? WHERE board_idx = ?";
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardDTO.getBoardTitle(),boardDTO.getBoardContent(),boardDTO.getBoardIdx()})){
                return dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("게시물 수정 중 오류가 발생하였습니다."+e);
        }
    }

//    @Override
//    public int insertBoard(BoardDTO boardDTO) throws SQLException{
//        String sql = "INSERT INTO tbl_board (board_type, board_title, board_content, board_writer) VALUES (?, ?, ?, ?)";
//        try(Connection conn = DBConnPool.getConnection();
//            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardDTO.getBoardType(),boardDTO.getBoardTitle(),boardDTO.getBoardContent(),boardDTO.getBoardWriter()})){
//                return dbUtil.executeUpdate();
//                
//                
//        }catch(SQLException e){
//            e.printStackTrace();
//            throw new RuntimeException("게시물 등록 중 오류가 발생하였습니다."+e);
//        }
//    }
    
    public int insertBoard(BoardDTO boardDTO) throws SQLException {
        String sql = "INSERT INTO tbl_board (board_type, board_title, board_content, board_writer, board_category) VALUES (?, ?, ?, ?, ?)";
        int boardIdx = 0;  // 새로 생성된 게시글의 ID를 저장할 변수
        System.out.println("카테고리:"+boardDTO.getBoardCategory());
        try (Connection conn = DBConnPool.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
        	pstm.setString(1, boardDTO.getBoardType());
        	pstm.setString(2, boardDTO.getBoardTitle());
        	pstm.setString(3, boardDTO.getBoardContent());
        	pstm.setString(4, boardDTO.getBoardWriter());
        	pstm.setString(5, boardDTO.getBoardCategory());
            
            int row = pstm.executeUpdate();
            
            if (row > 0) {
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                	boardIdx = rs.getInt(1);  
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 등록 중 오류가 발생하였습니다." + e);
        }
        
        return boardIdx;  
    }
    
    
 // 관리자용 전체 게시판 받아오기
    public List<BoardDTO> totalBoardList(int limit, int offset, String boardType, String searchType, String searchKeyword, String boardCategory) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM tbl_board");
        List<Object> params = new ArrayList<>();

        boolean hasWhere = false;

        // 게시글 타입 조건 추가
        if (boardType != null && !boardType.isEmpty()) {
            sql.append(" WHERE board_type = ?");
            params.add(boardType);
            hasWhere = true;
        }
        if (searchType != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            String searchParam = "%" + searchKeyword + "%";  
            if ("title".equals(searchType)) {
                sql.append(hasWhere ? " AND" : " WHERE").append(" board_title LIKE ?");
                params.add(searchParam);
            } else if ("writer".equals(searchType)) {
                sql.append(hasWhere ? " AND" : " WHERE").append(" board_writer LIKE ?");
                params.add(searchParam);
            } else if ("title_content".equals(searchType)) {
                sql.append(hasWhere ? " AND" : " WHERE").append(" (board_title LIKE ? OR board_content LIKE ?)");
                params.add(searchParam);
                params.add(searchParam);
            } else if ("category".equals(searchType)) { 
                sql.append(hasWhere ? " AND" : " WHERE").append(" board_category LIKE ?");
                params.add(searchParam);
            } else {
                throw new IllegalArgumentException("유효하지 않은 검색 타입입니다.");
            }
        }

        // 정렬 및 페이징 조건 추가
        sql.append(" ORDER BY board_idx DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        // 게시글 조회
        List<BoardDTO> boardList = new ArrayList<>();

        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params.toArray())) {

            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setBoardIdx(rs.getInt("board_idx"));
                boardDTO.setBoardType(rs.getString("board_type"));
                boardDTO.setBoardCategory(rs.getString("board_category"));
                boardDTO.setBoardTitle(rs.getString("board_title"));
                boardDTO.setBoardWriter(rs.getString("board_writer"));
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(rs.getTimestamp("board_regdate"));
                boardDTO.setBoardRegdate(formattedDate);

                boardList.add(boardDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 조회 중 오류가 발생하였습니다." + e);
        }
        return boardList;
    }
    
    // 관리자용 다중 삭제
    public int deleteByBoardIdxs(int[] boardIdxs) throws SQLException {
        String deleteCommentsSql = "DELETE FROM tbl_comment WHERE post_idx = ?";
        String deleteFileSql = "DELETE FROM tbl_file WHERE board_idx = ?";
        String deleteBoardSql = "DELETE FROM tbl_board WHERE board_idx = ?";

        try (Connection conn = DBConnPool.getConnection()) {
            conn.setAutoCommit(false); 

            try {
                try (PreparedStatement commentStmt = conn.prepareStatement(deleteCommentsSql)) {
                    for (int boardIdx : boardIdxs) {
                        commentStmt.setInt(1, boardIdx);
                        commentStmt.addBatch();
                    }
                    commentStmt.executeBatch(); 
                }
                
                try (PreparedStatement commentStmt = conn.prepareStatement(deleteFileSql)) {
                    for (int boardIdx : boardIdxs) {
                        commentStmt.setInt(1, boardIdx);
                        commentStmt.addBatch();
                    }
                    commentStmt.executeBatch(); 
                }

                try (PreparedStatement boardStmt = conn.prepareStatement(deleteBoardSql)) {
                    for (int boardIdx : boardIdxs) {
                        boardStmt.setInt(1, boardIdx);
                        boardStmt.addBatch(); 
                    }
                    int[] results = boardStmt.executeBatch(); 
                    conn.commit(); 
                    return results.length; 
                } catch (SQLException e) {
                    conn.rollback();  
                    throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다. " + e);
                }
            } catch (SQLException e) {
                conn.rollback();  
                throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다. " + e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("게시글 삭제 중 오류가 발생하였습니다. " + e);
        }
    }
    
    
}