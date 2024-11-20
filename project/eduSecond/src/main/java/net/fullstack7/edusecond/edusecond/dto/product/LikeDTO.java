package net.fullstack7.edusecond.edusecond.dto.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private int wishId;
    private String userId;
    private int productId;
    private LocalDateTime regDate;
}
