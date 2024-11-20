package net.fullstack7.edusecond.edusecond.service.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.notice.NoticeDTO;
import net.fullstack7.edusecond.edusecond.domain.notice.NoticeVO;
import net.fullstack7.edusecond.edusecond.mapper.NoticeMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class NoticeServiceImpl implements NoticeServiceIf {
    private final NoticeMapper noticeMapper;
    private final ModelMapper modelMapper;

    @Override
    public List<NoticeDTO> getList(int pageNo, int pageSize, String searchCategory, String searchValue) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("searchCategory", searchCategory);
        params.put("searchValue", searchValue);

        return noticeMapper.selectAllNotices(params)
                .stream()
                .map(vo -> modelMapper.map(vo, NoticeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalCount(String searchCategory, String searchValue) {
        Map<String, Object> params = new HashMap<>();
        params.put("searchCategory", searchCategory);
        params.put("searchValue", searchValue);
        return noticeMapper.totalCount(params);
    }

    @Override
    public boolean create(NoticeDTO notice) {
        try {
            noticeMapper.insertNotice(modelMapper.map(notice, NoticeVO.class));
            return true;
        } catch (Exception e) {
            log.error("공지사항 등록 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    public NoticeDTO getNotice(Integer noticeId) {
        NoticeVO vo = noticeMapper.selectNoticeById(noticeId);
        if (vo != null) {
            noticeMapper.updateViewCount(noticeId);
            return modelMapper.map(vo, NoticeDTO.class);
        }
        return null;
    }

    @Override
    public boolean update(NoticeDTO notice) {
        try {
            noticeMapper.updateNotice(modelMapper.map(notice, NoticeVO.class));
            return true;
        } catch (Exception e) {
            log.error("공지사항 수정 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    public boolean delete(Integer noticeId) {
        try {
            noticeMapper.deleteNotice(noticeId);
            return true;
        } catch (Exception e) {
            log.error("공지사항 삭제 중 오류 발생: ", e);
            return false;
        }
    }
}
