package net.haebup.dto.member.reportCard;

public class ReportCardDTO {
	private int reportCaedIdx;
	private String lectureCode;
	private String reportCardName;
	private String reportCardRegdate;
	private String userId;
	
	public int getReportCaedIdx() {
		return reportCaedIdx;
	}
	public void setReportCaedIdx(int reportCaedIdx) {
		this.reportCaedIdx = reportCaedIdx;
	}
	public String getLectureCode() {
		return lectureCode;
	}
	public void setLectureCode(String lectureCode) {
		this.lectureCode = lectureCode;
	}
	public String getReportCardName() {
		return reportCardName;
	}
	public void setReportCardName(String reportCardName) {
		this.reportCardName = reportCardName;
	}
	public String getReportCardRegdate() {
		return reportCardRegdate;
	}
	public void setReportCardRegdate(String reportCardRegdate) {
		this.reportCardRegdate = reportCardRegdate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
