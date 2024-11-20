package net.haebup.dto.qna;

public class QnaDTO {
	private int qnaIdx;				// QnA 아이디
	private String qnaTitle;		// QnA 제목
	private String qnaContent;		// QnA 내용
	private String qnaRegdate;		// QnA 등록일
	private String qnaWriter;		// QnA 작성자 아이디
	private String qnaType;			// QnA 유형 G : 일반 QnA, T : 선생님 QnA
	private String qnaCategory;		// QnA 카테고리 일반 QnA 유형 코드, 강의 코드
	
	
	public int getQnaIdx() {
		return qnaIdx;
	}
	public void setQnaIdx(int qnaIdx) {
		this.qnaIdx = qnaIdx;
	}
	public String getQnaTitle() {
		return qnaTitle;
	}
	public void setQnaTitle(String qnaTitle) {
		this.qnaTitle = qnaTitle;
	}
	public String getQnaContent() {
		return qnaContent;
	}
	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}
	public String getQnaRegdate() {
		return qnaRegdate;
	}
	public void setQnaRegdate(String qnaRegdate) {
		this.qnaRegdate = qnaRegdate;
	}
	public String getQnaWriter() {
		return qnaWriter;
	}
	public void setQnaWriter(String qnaWriter) {
		this.qnaWriter = qnaWriter;
	}
	public String getQnaType() {
		return qnaType;
	}
	public void setQnaType(String qnaType) {
		this.qnaType = qnaType;
	}
	public String getQnaCategory() {
		return qnaCategory;
	}
	public void setQnaCategory(String qnaCategory) {
		this.qnaCategory = qnaCategory;
	}
	
	

}
