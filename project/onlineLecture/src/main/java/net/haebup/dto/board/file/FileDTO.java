package net.haebup.dto.board.file;

public class FileDTO {
	private int fileIdx;		// 파일 아이디
	private String fileName;	// 파일 이름
	private String filePath;	// 파일 경로
	private int boardIdx;		// 게시물 아이디
	private int fileSize;		// 파일 사이즈

	public int getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

}
