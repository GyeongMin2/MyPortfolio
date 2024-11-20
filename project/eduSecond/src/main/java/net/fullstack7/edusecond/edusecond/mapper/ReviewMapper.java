package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.domain.review.ReviewVO;
import net.fullstack7.edusecond.edusecond.dto.review.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    int insertReview(ReviewVO reviewVO);
    List<ReviewVO> viewReview(Integer productId);
}
