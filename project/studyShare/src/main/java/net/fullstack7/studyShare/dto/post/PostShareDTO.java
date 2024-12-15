package net.fullstack7.studyShare.dto.post;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Log4j2
@Builder
public class PostShareDTO {
    private String id;
    private Integer postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime sharedCreatedAt;
    private String userId; // 공유 받은 사람
    private int isShared;

    // 좋아요 join
    private int likeCount;
}
