package net.haebup.dao.board.file;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.haebup.dto.board.file.FileDTO;
import net.haebup.utils.DatabaseUtil.*; 

public class FileDAO{
    //해당 게시물의 파일 조회
    public List<FileDTO> selectFileByBoardIdx(int boardIdx) throws SQLException{
    	System.out.println("파일조회시작");
        String sql = "SELECT * FROM tbl_file WHERE board_idx = ?";
        List<FileDTO> fileList = new ArrayList<>();
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{boardIdx})){
                ResultSet rs = dbUtil.executeQuery();
                while(rs.next()){
                    FileDTO fileDTO = new FileDTO();
                    fileDTO.setFileIdx(rs.getInt("file_idx"));
                    fileDTO.setBoardIdx(rs.getInt("board_idx"));
                    fileDTO.setFileName(rs.getString("file_name"));
                    fileDTO.setFilePath(rs.getString("file_path"));
                    fileList.add(fileDTO);
                }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("파일 조회 중 오류가 발생하였습니다."+e);
        }
        return fileList;
    }
    
    //해당 게시물의 파일 등록
    public int insertFile(FileDTO fileDTO) throws SQLException{
        String sql = "INSERT INTO tbl_file (board_idx, file_name, file_path,file_size) VALUES (?, ?, ?, ?)";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{fileDTO.getBoardIdx(), fileDTO.getFileName(), fileDTO.getFilePath(), fileDTO.getFileSize()})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("파일 등록 중 오류가 발생하였습니다."+e);
        }
        return result;
    }   
    
    //해당 게시물의 파일 삭제
    public int deleteFile(int fileIdx) throws SQLException{
        String sql = "DELETE FROM tbl_file WHERE file_idx = ?";
        int result = 0;
        try(Connection conn = DBConnPool.getConnection();
            DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{fileIdx})){
                result = dbUtil.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("파일 삭제 중 오류가 발생하였습니다."+e);
        }
        return result;
    }

}
