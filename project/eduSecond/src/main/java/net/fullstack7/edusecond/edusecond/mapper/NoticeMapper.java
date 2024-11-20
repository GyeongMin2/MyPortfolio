package net.fullstack7.edusecond.edusecond.mapper;

import java.util.List;
import java.util.Map;
import net.fullstack7.edusecond.edusecond.domain.notice.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {
    List<NoticeVO> selectAllNotices(Map<String, Object> params);
    int totalCount(Map<String, Object> params);
    void insertNotice(NoticeVO notice);
    NoticeVO selectNoticeById(Integer noticeId);
    void updateNotice(NoticeVO notice);
    void deleteNotice(Integer noticeId);
    void updateViewCount(Integer noticeId);
}
