package net.fullstack7.edusecond.edusecond.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}
