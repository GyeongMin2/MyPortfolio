package net.fullstack7.edusecond.edusecond.dto.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderListDTO {
    private int orderId;
    private int productId;
    private int unitPrice;
    private int orderQuantity;
    private int totalPrice;
    private String orderStatus;
    private String buyerId;
    private String recipientName;
    private String recipientPhone;
    private String recipientEmail;
    private String shippingAddress;
    private String shippingPostcode;
    private String paymentMethod;
    private String paymentCompany;
    private String paymentNumber;
    private String deliveryStatus;
    private LocalDateTime regDate;
    private String sellerId;
    private String productName;
    private String productDesc;
    private String productStatus;
    private String imagePath;
    private int reviewId;
}
