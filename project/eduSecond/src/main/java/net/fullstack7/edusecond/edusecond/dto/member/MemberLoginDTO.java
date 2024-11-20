package net.fullstack7.edusecond.edusecond.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {
    private String userId;
//    private String userPw;
    private String userName;
    private String enabled;
}
