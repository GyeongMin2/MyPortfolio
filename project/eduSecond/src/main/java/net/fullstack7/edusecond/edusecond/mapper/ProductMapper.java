package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.domain.product.ProductImageVO;
import net.fullstack7.edusecond.edusecond.domain.product.ProductVO;
import net.fullstack7.edusecond.edusecond.domain.product.ProductImageVO;
import net.fullstack7.edusecond.edusecond.dto.payment.PaymentDTO;
import org.apache.ibatis.annotations.Param;
import net.fullstack7.edusecond.edusecond.dto.product.ProductUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    ProductVO selectProductById(Integer productId);
    List<ProductImageVO> selectImagesByProductId(Integer productId);
    List<ProductVO> selectAllProducts(Map<String, Object> map);
    List<ProductVO> selectProductsBySeller(Map<String, Object> map);
    int insertProduct(ProductVO productVO);
    void insertProductImageMain(ProductImageVO productVO);
    void insertProductImage(ProductImageVO productVO);
    void deleteProduct(Integer productId);
    void updateViewCount(Integer productId);
    int getLastProductId();
    int totalCount(Map<String, Object> map);

    List<ProductImageVO> selectProductImages(int productId);

    ProductImageVO selectThumbnailImage(int productId);

    int reductionAfterPayment(PaymentDTO paymentDTO);

    List<ProductVO> selectAllWishByUser(Map<String, Object> map);
    int totalCountLikedProducts(Map<String, Object> map);

    List<ProductVO> selectAllByProductStatus(Map<String, Object> map);
    int totalCountByProductStatus(Map<String, Object> map);

    void updateProduct(ProductUpdateDTO productUpdateDTO);
    void deleteProductImage(int imageId);
    void updateMainImage(@Param("productId") Integer productId, @Param("newMainImageId") Integer newMainImageId);
    int hasMainImage(int productId);

    ProductImageVO selectImageById(Integer imageId);

    void updateProductImageMain(@Param("imageId") Integer imageId, @Param("isMain") String isMain);

    void updateAllImagesMain(@Param("productId") Integer productId, @Param("isMain") String isMain);
    
    void updateImageMain(@Param("imageId") Integer imageId, @Param("isMain") String isMain);
}
