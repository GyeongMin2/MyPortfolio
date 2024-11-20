package net.fullstack7.edusecond.edusecond.domain.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String userPostcode;
    private LocalDate userBirth;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
    private String enabled;
}