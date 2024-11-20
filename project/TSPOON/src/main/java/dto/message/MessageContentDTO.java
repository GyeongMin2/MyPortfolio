package dto.message;
public class MessageContentDTO {
    private int messageId;
    private String content;
    private String sendDate;
    private String readDate;
    private MessageFileDTO file;
    private String messageTitle;


    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
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

    public MessageFileDTO getFile() {
        return file;
    }

    public void setFile(MessageFileDTO file) {
        this.file = file;
    }
}