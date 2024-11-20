package net.haebup.dao.board;
import java.util.List;
import net.haebup.dto.board.BoardDTO;
import java.sql.SQLException;

public interface IFBoardDAO {

    //boardType : 게시물 유형 (P: 자유게시판, N: 공지사항, D: 자료실, C: 강의 공지, R: 수강후기)
    public List<BoardDTO> getBoardList(int limit, int offset, String boardType, String boardCategory, String boardWriter, String searchType, String searchKeyword) throws SQLException;

    public List<BoardDTO> getUserBoardList(int limit, int offset, String boardType, String userId) throws SQLException;

    public int getTotalCount(String boardType, String boardCategory, String boardWriter) throws SQLException;

    public int getUserTotalCount(String boardType, String userId) throws SQLException;

    public BoardDTO getBoardDetail(int boardIdx) throws SQLException;

    public int deleteByBoardIdx(int boardIdx) throws SQLException;

    public int updateBoard(BoardDTO boardDTO) throws SQLException;

    public int insertBoard(BoardDTO boardDTO) throws SQLException;



}
