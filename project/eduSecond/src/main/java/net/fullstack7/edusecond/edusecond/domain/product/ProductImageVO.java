package net.fullstack7.edusecond.edusecond.domain.product;

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
public class ProductImageVO {
    private Integer imageId;
    private Integer productId;
    private String imagePath;
    private String isMain;
} 