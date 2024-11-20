package net.fullstack7.edusecond.edusecond.dto.review;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDTO {
    private int reviewId;
    private int orderId;
    private String writerId;
    private String content;
    private int rating;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;


    //날짜 변형 용도
    private String formatRegDate;
    public String getFormatRegDate() {
        return formatRegDate;
    }
    public void setFormatRegDate(String formatRegDate) {
        this.formatRegDate = formatRegDate;
    }
}
