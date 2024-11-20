package net.fullstack7.edusecond.edusecond.dto.member;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyDTO {

    private String userId;

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
    private String userEmail;


    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
            message = "올바른 전화번호 형식이 아닙니다")
    private String userPhone;

    @NotBlank(message = "주소는 필수입니다")
    private String userAddress;

    @NotNull(message = "생년월일은 필수입니다")
    @Past(message = "생년월일은 과거 날짜여야 합니다")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirth;


    @NotBlank(message = "주소는 필수입니다")
    private String userPostcode;
}
