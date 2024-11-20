package net.haebup.dto.lecture;

public class ReportCardDTO {
    private int reportCardIdx;
    private String lectureCode;
    private String reportCardName;
    private String reportCardRegdate;
    private String userId;
    private String score;
    
    // 추가 필드 (조인용)
    private String userName;
    private String lectureName;
    
    // Getters and Setters
    public int getReportCardIdx() { return reportCardIdx; }
    public void setReportCardIdx(int reportCardIdx) { this.reportCardIdx = reportCardIdx; }
    
    public String getLectureCode() { return lectureCode; }
    public void setLectureCode(String lectureCode) { this.lectureCode = lectureCode; }
    
    public String getReportCardName() { return reportCardName; }
    public void setReportCardName(String reportCardName) { this.reportCardName = reportCardName; }
    
    public String getReportCardRegdate() { return reportCardRegdate; }
    public void setReportCardRegdate(String reportCardRegdate) { this.reportCardRegdate = reportCardRegdate; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getLectureName() { return lectureName; }
    public void setLectureName(String lectureName) { this.lectureName = lectureName; }
}
