package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MypageDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import net.fullstack7.edusecond.edusecond.domain.member.MemberVO;

@Mapper
public interface MemberMapper {
    MemberVO selectMemberById(String userId);
    MemberVO getLoginMember(String userId);
    int insertMember(MemberVO memberVO);
    int updateMember(MemberVO memberVO);
    int deleteMember(String userId);
    int selectTotalCount(@Param("searchCategory") String searchCategory,
                        @Param("searchValue") String searchValue);
    List<MemberVO> selectList(Map<String, Object> params);
    int updateEnabled(@Param("userId") String userId,
                     @Param("enabled") String enabled);
    MypageDTO myProductCount(String userId);
    int updateMemberWithdrawal(@Param("userId") String userId);
    int updateProductStatusByUserId(@Param("userId") String userId);
    List<MemberVO> selectWithdrawalList(Map<String, Object> params);
    int selectWithdrawalTotalCount(@Param("searchCategory") String searchCategory,
                                 @Param("searchValue") String searchValue);
} 
