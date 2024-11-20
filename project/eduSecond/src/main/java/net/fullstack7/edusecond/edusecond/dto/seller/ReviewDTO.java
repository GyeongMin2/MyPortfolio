package net.fullstack7.edusecond.edusecond.dto.seller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private int reviewId;
    private int orderId;
    private String writerId;
    private String content;
    private int rating;
    private Timestamp regDate;
    private Timestamp modifyDate;
    private int productId;
    private String productName;
}

