package net.fullstack7.edusecond.edusecond.dto.message;

import java.util.Date;  

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private int roomId;
    private String senderId;
    private String senderName;
    private String message;
    private String isRead;
    private Date regDate;
    private String type;
    private String targetId;
}