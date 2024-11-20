package net.fullstack7.edusecond.edusecond.service.notice;

import java.util.List;
import net.fullstack7.edusecond.edusecond.dto.notice.NoticeDTO;

public interface NoticeServiceIf {
  List<NoticeDTO> getList(int pageNo, int pageSize, String searchCategory, String searchValue);
  int getTotalCount(String searchCategory, String searchValue);
  boolean create(NoticeDTO notice);
  NoticeDTO getNotice(Integer noticeId);
  boolean update(NoticeDTO notice);
  boolean delete(Integer noticeId);
}