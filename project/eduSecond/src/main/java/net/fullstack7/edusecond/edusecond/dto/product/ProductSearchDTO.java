package net.fullstack7.edusecond.edusecond.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDTO {
    private String searchType;
    private String keyword;
    private String quality;
    private Integer minPrice;
    private Integer maxPrice;
    private String productStatus;
    private String sortBy = "regDate";
    private String sortDirection = "DESC";
    private int page = 1;
    private int size = 10;
} 