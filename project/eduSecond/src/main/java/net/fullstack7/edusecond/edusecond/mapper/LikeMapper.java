package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.domain.product.LikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    void insertLike(@Param("userId") String userId, @Param("productId") Integer productId);
    void deleteLike(@Param("userId") String userId, @Param("productId") Integer productId);

    boolean checkExists(@Param("userId") String userId, @Param("productId") Integer productId);
    //특정 조회
    LikeVO getLike(Integer productId, String userId);
    int deleteByUserId(@Param("userId") String userId);
}
