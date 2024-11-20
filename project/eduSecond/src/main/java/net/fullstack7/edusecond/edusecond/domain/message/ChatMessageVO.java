package net.fullstack7.edusecond.edusecond.domain.message;

import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVO {
    private int messageId;
    private int roomId;
    private String senderId;
    private String message;
    private String isRead;
    private Date regDate;
} 