package net.fullstack7.edusecond.edusecond.service.Like;

import org.apache.ibatis.annotations.Param;

public interface LikeServiceIf {

    public void insertLike(String userId, Integer productId);
    public void deleteLike(String userId, Integer productId);

    public void getLike(Integer productId, String userId);

    boolean checkExists(String userId, Integer productId);
}
