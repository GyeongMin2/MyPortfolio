package net.fullstack7.edusecond.edusecond.dto;

import javax.validation.constraints.NotBlank;
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
public class AdminLoginDTO {
    @NotBlank(message = "관리자 ID는 필수입니다")
    private String adminId;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String adminPw;
    
    private boolean rememberMe;
} 