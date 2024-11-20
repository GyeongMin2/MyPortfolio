package net.fullstack7.edusecond.edusecond.service.member;

import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberModifyDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberRegistDTO;
import java.util.List;

public interface MemberServiceIf {
  MemberDTO getMember(String userId);
  boolean registerMember(MemberRegistDTO registDTO);
  boolean loginMember(String userId, String userPw);
  int getTotalCount(String searchCategory, String searchValue);
  List<MemberDTO> getList(int pageNo, int pageSize, String searchCategory, String searchValue);
  boolean updateEnabled(String userId, String enabled);
  boolean deleteMember(String userId);
  int modifyMember(MemberModifyDTO memberModifyDTO);
  MemberLoginDTO getLoginMember(String userId);
  boolean processWithdrawal(String userId);
  boolean requestWithdrawal(String userId);
  List<MemberDTO> getWithdrawalList(int pageNo, int pageSize, String searchCategory, String searchValue);
  int getWithdrawalTotalCount(String searchCategory, String searchValue);
}
