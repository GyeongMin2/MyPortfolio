package net.fullstack7.edusecond.edusecond.dto.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private int roomId;
    private int productId;
    private String sellerId;
    private String buyerId;
    
    private String productName;
    private String productImage;
    private String lastMessage;
    private Date lastMessageDate;
    private int unreadCount;
    private String otherUserName; // 상대방 이름
}