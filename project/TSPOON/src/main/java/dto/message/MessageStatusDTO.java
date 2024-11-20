package dto.message;

public class MessageStatusDTO {
    private int messageStatusId;
    private int messageId;
    private String userId;
    private int isSender;  //1: 발신, 2: 수신
    private int readStatus;
    private int deleteStatus;

    public int getMessageStatusId() {
        return messageStatusId;
    }

    public void setMessageStatusId(int messageStatusId) {
        this.messageStatusId = messageStatusId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsSender() {
        return isSender;
    }

    public void setIsSender(int isSender) {
        this.isSender = isSender;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
