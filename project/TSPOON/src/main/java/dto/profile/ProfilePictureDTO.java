package dto.profile;

public class ProfilePictureDTO {
    private int profileId;
    private String userId;
    private String fileName;
    private String filePath;
    private String uploadDate;
    // private int isCurrent;  // 1: 사용 중, 0: 사용 안함, 생각해보니까 안쓸듯 
    private long fileSize;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    // public int getIsCurrent() {
    //     return isCurrent;
    // }

    // public void setIsCurrent(int isCurrent) {
    //     this.isCurrent = isCurrent;
    // }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
