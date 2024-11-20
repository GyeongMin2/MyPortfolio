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
public class ChatRoomVO {
    private int roomId;
    private int productId;
    private String sellerId;
    private String buyerId;
    private String sellerActive;
    private String buyerActive;
    private Date regDate;
} 