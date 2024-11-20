package net.fullstack7.edusecond.edusecond.service.product;

import net.fullstack7.edusecond.edusecond.domain.product.ProductImageVO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductImageDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductRegistDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductServiceIf {

    List<ProductDTO> list(int pageNo, int pageSize, int blockSize, String searchCategory, String searchValue, String productStatus);

     ProductDTO view(Integer productId);
    public int totalCount(String searchCategory, String searchValue, String productStatus);

    public int totalCountLikedProducts(String searchCategory, String searchValue, String userId);
    List<ProductImageDTO> getProductImages(int productId);
    ProductImageDTO getThumbnailImage(int productId);
    int insertProduct(ProductRegistDTO productRegistDTO);
    int getLastProductId();
    void insertProductImage(int productId, List<String> uploadFilePaths);

    List<ProductDTO> selectAllByUser(int pageNo, int pageSize, int pageNavSize, String searchCategory, String searchValue, String userId, String type);

    List<ProductDTO> selectAllByProductStatus(int pageNo, int pageSize, int pageNavSize, String searchCategory, String searchValue, String userId, String productStatus);
    public int totalCountByProductStatus(String searchCategory, String searchValue, String userId, String productStatus);
    public void deleteProduct(int productId);
    int updateProduct(ProductUpdateDTO productUpdateDTO, List<MultipartFile> files) throws IOException;
    void updateViewCount(Integer productId);
}
