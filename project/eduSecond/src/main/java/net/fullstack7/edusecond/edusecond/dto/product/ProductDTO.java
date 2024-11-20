package net.fullstack7.edusecond.edusecond.dto.product;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer productId;
    private String sellerId;
    private String sellerName;    // 판매자 이름
    private String productName;
    private String productDesc;
    private Integer price;
    private Integer quantity;
    private String quality;
    private String productStatus;
    private Integer viewCount;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    
    // 추가 정보
    private ProductImageDTO thumbnail;  // 썸네일 이미지
    private List<ProductImageDTO> images;  // 모든 이미지
    private Integer wishCount;        // 찜 수
    private Double avgRating;         // 판매자 평균 평점
    private Integer reviewCount;      // 판매자 리뷰 수

    //날짜 변형 용도
    private String formatRegDate;
    public String getFormatRegDate() {
        return formatRegDate;
    }
    public void setFormatRegDate(String formatRegDate) {
        this.formatRegDate = formatRegDate;
    }
}
