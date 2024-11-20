package net.fullstack7.edusecond.edusecond.dto.seller;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private String sellerId;         // 판매자 ID
    private String sellerName;       // 판매자 이름
    private String sellerEmail;      // 판매자 이메일

    // Product Information
    private Long productId;          // 상품 ID
    private String productName;      // 상품 이름
    private String productDesc;      // 상품 설명
    private Double price;            // 가격
    private Integer quantity;        // 수량
    private String quality;          // 품질
    private String productStatus;    // 상품 상태
    private Integer viewCount;       // 조회수

    // Product Image Information
    private String productImage;     // 상품 이미지 경로
    private Boolean isMainImage;     // 메인 이미지 여부

    // Review Information
    //private Long reviewId;           // 리뷰 ID
    //private String reviewContent;    // 리뷰 내용
    //private Double reviewRating;     // 리뷰 평점
    //private String reviewDate;       // 리뷰 등록일
    //private String reviewWriterId;   // 리뷰 작성자 ID
}
