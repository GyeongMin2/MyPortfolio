package net.haebup.dto.member;

public class MemberTypeDTO {
	
	private String userType;
    private String userTypeName;
    // private String memberTypeDescription;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    // public String getMemberTypeDescription() {
    //     return memberTypeDescription;
    // }

    // public void setMemberTypeDescription(String memberTypeDescription) {
    //     this.memberTypeDescription = memberTypeDescription;
    // }
}
