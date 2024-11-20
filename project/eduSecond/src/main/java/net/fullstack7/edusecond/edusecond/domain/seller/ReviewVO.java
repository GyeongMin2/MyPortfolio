package net.fullstack7.edusecond.edusecond.domain.seller;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVO {
    private int reviewId;
    private int orderId;
    private String writerId;
    private String content;
    private int rating;
    private Timestamp regDate;
    private Timestamp modifyDate;
}

