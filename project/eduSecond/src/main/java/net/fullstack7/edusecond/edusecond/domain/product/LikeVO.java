package net.fullstack7.edusecond.edusecond.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeVO {
    private int wishId;
    private String userId;
    private int productId;
    private LocalDateTime regDate;
}
