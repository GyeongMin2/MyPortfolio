package net.fullstack7.edusecond.edusecond.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.domain.review.ReviewVO;
import net.fullstack7.edusecond.edusecond.dto.review.ReviewDTO;
import net.fullstack7.edusecond.edusecond.mapper.ReviewMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewServiceIf {
    private final ReviewMapper reviewMapper;
    private final ModelMapper modelMapper;

    @Override
    public int insertReview(ReviewDTO reviewDTO) {
        ReviewVO reviewVO = modelMapper.map(reviewDTO, ReviewVO.class);
        return reviewMapper.insertReview(reviewVO);

    }

    @Override
    public List<ReviewDTO> viewReview(int productId) {
        List<ReviewVO> voList = reviewMapper.viewReview(productId);

        //vo ->List
        List<ReviewDTO> dtoList = voList.stream().map(vo -> modelMapper.map(vo, ReviewDTO.class)).collect(Collectors.toList());
        return dtoList;
    }
}
