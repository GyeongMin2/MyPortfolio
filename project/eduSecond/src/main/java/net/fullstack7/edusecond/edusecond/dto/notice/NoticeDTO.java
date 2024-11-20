package net.fullstack7.edusecond.edusecond.dto.notice;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Integer noticeId;
    private String adminId;
    private String title;
    private String content;
    private Integer viewCount;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
