package net.fullstack7.edusecond.edusecond.dto.payment;

import lombok.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private int orderId;
    private int productId;
    private int unitPrice;
    private int totalPrice;
    private String buyerId;

    @NotNull(message = "결제 수량을 입력해주세요")
    @Min(value = 1, message = "결제 수량은 1 이상이어야 합니다")
    private Integer orderQuantity;

    @NotBlank(message = "받는 사람 이름을 입력해주세요")
    private String recipientName;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호를 형식에 맞춰서 입력해주세요 예시 : 010-1234-5678")
    private String recipientPhone;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식에 맞춰서 입력해주세요")
    private String recipientEmail;

    @NotBlank(message = "배송 주소는 필수입니다")
    private String shippingAddress;

    @NotBlank(message = "우편번호는 필수입니다")
    private String shippingPostcode;

    @NotBlank(message = "결제 회사를 입력해주세요")
    private String paymentCompany;

    private String paymentMethod;
    private String paymentNumber;
    private String deliveryStatus;
    private LocalDateTime regDate;
    private String orderStatus;

}

