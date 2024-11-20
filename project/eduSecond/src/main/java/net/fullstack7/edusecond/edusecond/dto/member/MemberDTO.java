package net.fullstack7.edusecond.edusecond.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String userPostcode;
    private LocalDate userBirth;
    
    // 추가 필드
    private int orderCount;
    private int reviewCount;
    private double avgRating;
    private List<ProductDTO> wishlist;
    private String enabled;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    
    // public boolean isEnabled() {
    //     return "Y".equalsIgnoreCase(enabled);
    // }
}