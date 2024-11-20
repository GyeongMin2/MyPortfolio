package net.fullstack7.edusecond.edusecond.service.payment;

import net.fullstack7.edusecond.edusecond.dto.payment.PaymentDTO;

public interface PaymentServiceIf {
    int insert(PaymentDTO dto);
    int insert_1(PaymentDTO dto);
}
