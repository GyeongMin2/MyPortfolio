package net.fullstack7.edusecond.edusecond.mapper;

import net.fullstack7.edusecond.edusecond.domain.PaymentVO;
import net.fullstack7.edusecond.edusecond.domain.product.ProductVO;
import net.fullstack7.edusecond.edusecond.dto.payment.PaymentDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    int insert(PaymentVO vo);
    int insert_1(PaymentVO vo);
    PaymentDTO getPaymentInfo(String paymentNumber);
}
