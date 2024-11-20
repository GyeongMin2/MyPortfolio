package net.fullstack7.edusecond.edusecond.dto.product;

import java.util.List;

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
public class ProductUpdateDTO {
    private Integer productId;
    private String productName;
    private String productDesc;
    private Integer price;
    private Integer quantity;
    private String quality;
    private String productStatus;
    private String imageUpdateType;
}
