package net.fullstack7.edusecond.edusecond.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    private Integer imageId;
    private Integer productId;
    private String imagePath;
    private String isMain;    // 'Y' or 'N'
}