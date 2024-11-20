package dto.message;

//메시지 가져올때 ( 디테일 갖고올때 X )
public class MessageDTO {
    private int messageId;
    private String content;
    private String sendDate;
    private String readDate;
    private boolean hasFileAttachment;
    private MessageStatusDTO status;
    private MessageFileDTO file;
    private String messageTitle;
    private String otherUserId;
    private String senderId;
    private String receiverId;
    
    

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public boolean getHasFileAttachment() {
        return hasFileAttachment;
    }

    public void setHasFileAttachment(boolean hasFileAttachment) {
        this.hasFileAttachment = hasFileAttachment;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public MessageStatusDTO getStatus() {
        return status;
    }

    public void setStatus(MessageStatusDTO status) {
        this.status = status;
    }

    public MessageFileDTO getFile() {
        return file;
    }

    public void setFile(MessageFileDTO file) {
        this.file = file;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
