package net.fullstack7.edusecond.edusecond.domain.notice;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {
    private Integer noticeId;
    private String adminId;
    private String title;
    private String content;
    private Integer viewCount;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
