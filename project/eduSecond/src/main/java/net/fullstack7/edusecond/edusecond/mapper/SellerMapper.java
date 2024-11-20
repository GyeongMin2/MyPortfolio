package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.domain.product.ProductVO;
import net.fullstack7.edusecond.edusecond.dto.seller.SellerDTO;
import net.fullstack7.edusecond.edusecond.dto.seller.StarAvgDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface SellerMapper {
    List<SellerDTO> selectProductInfo(@Param("sellerId") String sellerId);
    List<SellerDTO> selectReviewInfo(@Param("sellerId") String sellerId);
    StarAvgDTO selectReviewStar(@Param("sellerId") String sellerId);
}
