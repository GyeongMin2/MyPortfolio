package dto.member;

public class MemberDTO {
    private String userId;
    private String email;
    private String password;
    private String salt;
    private String name;
    private String birthday;
    private String gender;  // 'M' or 'F'
    private String phone;
    private String interest;
    private String grade;
    private int locationAgreement;
    private int thirdpartyAgreement;
    private int promotionAgreement;
    private int chunjaeEduAgreement;
    private String memberStatus;  // 'T', 'N', 'Y'
    private String regDate;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getLocationAgreement() {
        return locationAgreement;
    }

    public void setLocationAgreement(int locationAgreement) {
        this.locationAgreement = locationAgreement;
    }

    public int getThirdpartyAgreement() {
        return thirdpartyAgreement;
    }

    public void setThirdpartyAgreement(int thirdpartyAgreement) {
        this.thirdpartyAgreement = thirdpartyAgreement;
    }

    public int getPromotionAgreement() {
        return promotionAgreement;
    }

    public void setPromotionAgreement(int promotionAgreement) {
        this.promotionAgreement = promotionAgreement;
    }

    public int getChunjaeEduAgreement() {
        return chunjaeEduAgreement;
    }

    public void setChunjaeEduAgreement(int chunjaeEduAgreement) {
        this.chunjaeEduAgreement = chunjaeEduAgreement;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}