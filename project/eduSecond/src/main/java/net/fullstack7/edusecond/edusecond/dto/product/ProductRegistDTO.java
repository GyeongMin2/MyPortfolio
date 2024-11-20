package net.fullstack7.edusecond.edusecond.dto.product;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegistDTO {

    private String sellerId;

    @NotBlank(message = "상품명은 필수입니다")
    private String productName;

    @NotBlank(message = "상품 설명은 필수입니다")
    private String productDesc;

    @NotNull(message = "가격은 필수입니다")
    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private Integer price;

    @NotNull(message = "수량은 필수입니다")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다")
    private Integer quantity;

    @NotBlank(message = "상품 상태는 필수입니다")
    @Pattern(regexp = "^(NEW|LIKE_NEW|GOOD|FAIR)$", message = "상품 상태는 NEW, LIKE_NEW, GOOD, FAIR 중 하나여야 합니다")
    private String quality;

    private List<String> imagePaths;  // 상품 이미지 경로들
    private int productId; // 상품 이미지 넣을 때 필요함
}