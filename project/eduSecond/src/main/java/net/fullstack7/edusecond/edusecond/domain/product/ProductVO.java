package net.fullstack7.edusecond.edusecond.domain.product;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {
    private Integer productId;
    private String sellerId;
    private String productName;
    private String productDesc;
    private Integer price;
    private Integer quantity;
    private String quality;        // 상품 상태 (ex: NEW, LIKE_NEW, GOOD, FAIR)
    private String productStatus;  // 판매 상태 (ex: AVAILABLE, RESERVED, SOLD)
    private Integer viewCount;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private String sellerName;
    private String thumbnailPath;
    private List<ProductImageVO> imageList;
} 