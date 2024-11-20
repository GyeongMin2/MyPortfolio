package net.haebup.dto.board;

public class BoardDTO {
	private int boardIdx;				// 게시물 아이디
	private String boardTitle;			// 게시물 제목
	private String boardContent;		// 게시물 내용
	private String boardRegdate;		// 게시물 등록일
	private String boardType;			// 게시물 유형 (P: 자유게시판, N: 공지사항, D: 자료실, C: 강의 공지, R: 수강후기)
	private String boardWriter;			// 게시물 작성자 아이디
	private String boardCategory;		// 게시물 카테고리 ( 강의코드 )
	
	
	
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardRegdate() {
		return boardRegdate;
	}
	public void setBoardRegdate(String boardRegdate) {
		this.boardRegdate = boardRegdate;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public String getBoardCategory() {
		return boardCategory;
	}
	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}
	

}
