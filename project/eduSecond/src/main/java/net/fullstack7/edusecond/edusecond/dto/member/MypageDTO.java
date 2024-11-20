package net.fullstack7.edusecond.edusecond.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MypageDTO {
    private int productAvailableCount;
    private int productReservedCount;
    private int productSoldCount;
    private int reviewCount;
    private int wishListCount;
}
