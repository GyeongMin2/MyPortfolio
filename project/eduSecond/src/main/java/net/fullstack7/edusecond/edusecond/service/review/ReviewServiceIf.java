package net.fullstack7.edusecond.edusecond.service.review;

import net.fullstack7.edusecond.edusecond.dto.review.ReviewDTO;

import java.util.List;

public interface ReviewServiceIf {
    public int insertReview(ReviewDTO reviewDTO);

    public List<ReviewDTO> viewReview(int productId);
}
