package net.fullstack7.edusecond.edusecond.service.Like;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.domain.product.LikeVO;
import net.fullstack7.edusecond.edusecond.dto.product.LikeDTO;
import net.fullstack7.edusecond.edusecond.mapper.LikeMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
@Log4j2
public class LikeServiceImpl implements LikeServiceIf{

    public final ModelMapper modelMapper;
    public final LikeMapper likeMapper;

    @Override
    public void insertLike(String userId, Integer productId) {
        boolean result = likeMapper.checkExists(userId, productId);
        log.info("like result: " + result);
        if (result){
            throw new IllegalArgumentException("이미 좋아요를 누름");
        }
        else{
            likeMapper.insertLike(userId, productId);
        }
    }

    @Override
    public void deleteLike(String userId, Integer productId) {
        likeMapper.deleteLike(userId, productId);
    }

    @Override
    public void getLike(Integer productId, String userId) {
        likeMapper.getLike(productId, userId);
    }

    @Override
    public boolean checkExists(String userId, Integer productId) {
        boolean result = likeMapper.checkExists(userId, productId);
        return result;
    }

}
