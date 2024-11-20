package net.haebup.dto.member.payment;

public class PaymentDTO {
	private int paymentIdx;
    private String userId;
    private String lectureCode;
    private String paymentDate;
    private String paymentStatus;
    private String lectureStartDate;
    private String lectureName;
    private String userName;
    private String userNickname;
    private String userEmail;
    private int lecturePrice;

    public int getLecturePrice() {
        return lecturePrice;
    }

    public void setLecturePrice(int lecturePrice) {
        this.lecturePrice = lecturePrice;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public int getPaymentIdx() {
        return paymentIdx;
    }
    public void setPaymentIdx(int paymentIdx) {
        this.paymentIdx = paymentIdx;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLectureCode() {
        return lectureCode;
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getLectureStartDate() {
        return lectureStartDate;
    }

    public void setLectureStartDate(String lectureStartDate) {
        this.lectureStartDate = lectureStartDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
