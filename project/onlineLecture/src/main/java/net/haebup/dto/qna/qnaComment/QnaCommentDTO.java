package net.haebup.dto.qna.qnaComment;

public class QnaCommentDTO {
	private int qnaCommentIdx;			// QnA 댓글 아이디
	private String qnaCommentContent;	// QnA 댓글 내용
	private String qnaCommentRegdate;	// QnA 댓글 등록일
	private int qnaIdx;					// QnA 아이디
	private String qnaCommentWriter;	// QnA 댓글 작성자 아이디
	
	public int getQnaCommentIdx() {
		return qnaCommentIdx;
	}
	public void setQnaCommentIdx(int qnaCommentIdx) {
		this.qnaCommentIdx = qnaCommentIdx;
	}
	public String getQnaCommentContent() {
		return qnaCommentContent;
	}
	public void setQnaCommentContent(String qnaCommentContent) {
		this.qnaCommentContent = qnaCommentContent;
	}
	public String getQnaCommentRegdate() {
		return qnaCommentRegdate;
	}
	public void setQnaCommentRegdate(String qnaCommentRegdate) {
		this.qnaCommentRegdate = qnaCommentRegdate;
	}
	public int getQnaIdx() {
		return qnaIdx;
	}
	public void setQnaIdx(int qnaIdx) {
		this.qnaIdx = qnaIdx;
	}
	public String getQnaCommentWriter() {
		return qnaCommentWriter;
	}
	public void setQnaCommentWriter(String qnaCommentWriter) {
		this.qnaCommentWriter = qnaCommentWriter;
	}
	
	

}
