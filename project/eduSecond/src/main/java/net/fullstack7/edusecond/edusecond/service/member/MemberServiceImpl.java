package net.fullstack7.edusecond.edusecond.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.domain.member.MemberVO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberModifyDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberRegistDTO;
import net.fullstack7.edusecond.edusecond.mapper.MemberMapper;
import net.fullstack7.edusecond.edusecond.mapper.ChatMapper;
import net.fullstack7.edusecond.edusecond.mapper.LikeMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberServiceIf {
    private final MemberMapper memberMapper;
    private final ModelMapper modelMapper;
    private final ChatMapper chatMapper;
    private final LikeMapper likeMapper;
    
    @Override
    public MemberDTO getMember(String userId) {
        MemberVO vo = memberMapper.selectMemberById(userId);
        return modelMapper.map(vo, MemberDTO.class);
    }
    
    @Override
    public boolean registerMember(MemberRegistDTO registDTO) {
        try {
            MemberVO vo = modelMapper.map(registDTO, MemberVO.class);
            return memberMapper.insertMember(vo) > 0;
        } catch(Exception e) {
            log.error("회원가입 실패: ", e);
            return false;
        }
    }
    
    @Override
    public boolean loginMember(String userId, String userPw) {
        MemberVO vo = memberMapper.selectMemberById(userId);
        if(vo != null) {
            return vo.getUserPw().equals(userPw);
        }
        return false;
    }


    
    @Override
    public int getTotalCount(String searchCategory, String searchValue) {
        return memberMapper.selectTotalCount(searchCategory, searchValue);
    }
    
    @Override
    public List<MemberDTO> getList(int pageNo, int pageSize, String searchCategory, String searchValue) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (pageNo - 1) * pageSize);
        params.put("limit", pageSize);
        params.put("searchCategory", searchCategory);
        params.put("searchValue", searchValue);
        
        List<MemberVO> voList = memberMapper.selectList(params);
        return voList.stream()
                .map(vo -> modelMapper.map(vo, MemberDTO.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean updateEnabled(String userId, String enabled) {
        return memberMapper.updateEnabled(userId, enabled) > 0;
    }
    
    @Override
    public boolean deleteMember(String userId) {
        return memberMapper.deleteMember(userId) > 0;
    }

    @Override
    public int modifyMember(MemberModifyDTO memberModifyDTO) {
        MemberVO vo = modelMapper.map(memberModifyDTO, MemberVO.class);
        return memberMapper.updateMember(vo);
    }

    @Override
    public MemberLoginDTO getLoginMember(String userId){
        MemberVO vo = memberMapper.getLoginMember(userId);
        return modelMapper.map(vo, MemberLoginDTO.class);
    }

    @Override
    @Transactional
    public boolean processWithdrawal(String userId) {
        try {
            // 1. 회원 상태를 'N'으로 변경하고 이름을 "(탈퇴한 회원)"으로 변경
            memberMapper.updateMemberWithdrawal(userId);
            
            // 2. 회원의 모든 상품 상태를 UNAVAILABLE로 변경
            memberMapper.updateProductStatusByUserId(userId);
            
            // 3. 관련된 모든 채팅방 상태를 'N'으로 변경
            chatMapper.updateChatRoomsByUserId(userId);
            
            // 4. 회원의 위시리스트 삭제
            likeMapper.deleteByUserId(userId);
            
            return true;
        } catch (Exception e) {
            log.error("회원 탈퇴 처리 중 오류 발생: ", e);
            throw new RuntimeException("회원 탈퇴 처리 실패", e);
        }
    }

    @Override
    public boolean requestWithdrawal(String userId) {
        try {
            return memberMapper.updateEnabled(userId, "Q") > 0;
        } catch (Exception e) {
            log.error("회원 탈퇴 신청 처리 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    public List<MemberDTO> getWithdrawalList(int pageNo, int pageSize, String searchCategory, String searchValue) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", (pageNo - 1) * pageSize);
        params.put("limit", pageSize);
        params.put("searchCategory", searchCategory);
        params.put("searchValue", searchValue);
        params.put("enabled", "Q");  // 탈퇴 신청 상태
        
        List<MemberVO> voList = memberMapper.selectWithdrawalList(params);
        return voList.stream()
                .map(vo -> modelMapper.map(vo, MemberDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getWithdrawalTotalCount(String searchCategory, String searchValue) {
        return memberMapper.selectWithdrawalTotalCount(searchCategory, searchValue);
    }
}
