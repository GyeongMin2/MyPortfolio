package net.fullstack7.edusecond.edusecond.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.payment.PaymentDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import net.fullstack7.edusecond.edusecond.mapper.OrderMapper;
import net.fullstack7.edusecond.edusecond.mapper.PaymentMapper;
import net.fullstack7.edusecond.edusecond.mapper.ProductMapper;
import net.fullstack7.edusecond.edusecond.service.payment.PaymentServiceIf;
import net.fullstack7.edusecond.edusecond.service.product.ProductServiceIf;
import net.fullstack7.edusecond.edusecond.util.JSFunc;
import org.mariadb.jdbc.plugin.codec.UuidCodec;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/es/payment")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {
    private final ProductServiceIf productService;
    private final PaymentServiceIf paymentService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    @GetMapping("/view")
    public String view(Model model, int productId, HttpServletResponse response){
        try{
            if(productId <= 0){
                JSFunc.alertBack("유효하지 않은 상품입니다", response);

                return null;
            }
            ProductDTO dto =  productService.view(productId);
            if(dto == null){
                JSFunc.alertBack("존재하지 않는 상품입니다", response);
            }
            model.addAttribute("dto", dto);
            return "payment/pay";
        }catch (Exception e){
            log.error("상품 결제창 조회 중 오류 발생: ", e);
            JSFunc.alertBack("상품 결제창을 불러오는 중 오류가 발생했습니다.", response);
            return null;
        }
    }

    @PostMapping("/pay")
    public String pay(HttpSession session,
                      @Valid PaymentDTO paymentDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @RequestParam int totalQuantity,
                      HttpServletResponse response){

        if(paymentDTO.getOrderQuantity() != null && paymentDTO.getOrderQuantity() > totalQuantity){
            JSFunc.alertBack("최대 수량을 넘길 수 없습니다.", response);
            return null;
        }
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/es/payment/view?productId=" + paymentDTO.getProductId();
        }

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        paymentDTO.setBuyerId(memberLoginDTO.getUserId());

        String paymentNumber = UUID.randomUUID().toString();
        paymentDTO.setPaymentNumber(paymentNumber);
        paymentDTO.setPaymentMethod("신용카드");
        int totalPrice = paymentDTO.getUnitPrice() * paymentDTO.getOrderQuantity();
        paymentDTO.setTotalPrice(totalPrice);

        int result = paymentService.insert(paymentDTO);
        if(result > 0){
            return "redirect:/es/mypage/orderList";
        } else{
            log.info("상품 결제  중 오류 발생");
            JSFunc.alertBack("상품 결제 요청 실패", response);
            return null;
        }
    }

    @GetMapping("view_1")
    public String view_1(Model model, int productId, HttpServletResponse response){
        try{
            if(productId <= 0){
                JSFunc.alertBack("유효하지 않은 상품입니다", response);
                return null;
            }
            ProductDTO dto =  productService.view(productId);
            if(dto == null){
                JSFunc.alertBack("존재하지 않는 상품입니다", response);
            }
            model.addAttribute("dto", dto);
            return "payment/pay_1";
        }catch (Exception e){
            log.error("상품 상세 조회 중 오류 발생: ", e);
            JSFunc.alertBack("상품 정보를 불러오는 중 오류가 발생했습니다.", response);
            return null;
        }
    }

    @PostMapping("/pay_1")
    public String pay_1(HttpSession session,
                        @Valid PaymentDTO paymentDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        @RequestParam int totalQuantity,
                        HttpServletResponse response){

        if(paymentDTO.getOrderQuantity() != null && paymentDTO.getOrderQuantity() > totalQuantity){
            JSFunc.alertBack("최대 수량을 넘길 수 없습니다.", response);
            return null;
        }
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/es/payment/view_1?productId=" + paymentDTO.getProductId();
        }
        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        paymentDTO.setBuyerId(memberLoginDTO.getUserId());

        String paymentNumber = UUID.randomUUID().toString();
        paymentDTO.setPaymentNumber(paymentNumber);

        int totalPrice = paymentDTO.getUnitPrice() * paymentDTO.getOrderQuantity();
        paymentDTO.setTotalPrice(totalPrice);
        paymentDTO.setPaymentMethod("직거래");
        int result = paymentService.insert_1(paymentDTO);
        if(result > 0){
            return "redirect:/es/mypage/orderList";
        } else{
            log.info("상품 결제  중 오류 발생");
            JSFunc.alertBack("상품 결제 요청 실패", response);
            return null;
        }
    }




    @GetMapping("/confirmPurchase")
    public String confirmPurchase(@RequestParam("paymentNumber") String paymentNumber,
                                  @RequestParam(defaultValue = "1") int pageNo, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        int result = orderMapper.confirmPurchase(paymentNumber, "배송완료/구매확정");
        if (result > 0) {
            JSFunc.alertLocation("구매가 확정되었습니다.","/es/mypage/orderList?pageNo="+pageNo, response);
        } else {
            JSFunc.alertBack("구매 확정에 실패했습니다", response);
        }
        return null;
    }


    @GetMapping("/startDelivery")
    public String startDelivery(@RequestParam("paymentNumber") String paymentNumber,
                                  @RequestParam(defaultValue = "1") int pageNo, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        int result = orderMapper.startDelivery(paymentNumber, "배송중");
        if (result > 0) {
            JSFunc.alertLocation("배송 시작.","/es/mypage/orderList_1?pageNo="+pageNo, response);
        } else {
            JSFunc.alertBack("배송 시작 실패", response);
        }
        return null;
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("paymentNumber") String paymentNumber,
                                @RequestParam(defaultValue = "1") int pageNo, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        int result = orderMapper.confirm(paymentNumber);

        if (result > 0) {
            PaymentDTO paymentDTO = paymentMapper.getPaymentInfo(paymentNumber);
            int result1 = productMapper.reductionAfterPayment(paymentDTO); // 상품정보에서 개수 차감하는 메서드

            if(result1 >0){
                JSFunc.alertLocation("구매 수락/상품 개수 차감.","/es/mypage/orderList_1?pageNo="+pageNo, response);
            } else{
                JSFunc.alertLocation("구매 수락/상품 개수 차감 오류. 수정 필요","/es/mypage/orderList_1?pageNo="+pageNo, response);
            }
        } else {
            JSFunc.alertBack("구매 수락 실패", response);
        }
        return null;
    }

    @GetMapping("/reject")
    public String reject(@RequestParam("paymentNumber") String paymentNumber,
                          @RequestParam(defaultValue = "1") int pageNo, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        int result = orderMapper.reject(paymentNumber);
        if (result > 0) {
            JSFunc.alertLocation("구매 거부.","/es/mypage/orderList_1?pageNo="+pageNo, response);
        } else {
            JSFunc.alertBack("구매 거부 실패", response);
        }
        return null;
    }

    @GetMapping("/direct")
    public String direct(@RequestParam("paymentNumber") String paymentNumber,
                          @RequestParam(defaultValue = "1") int pageNo, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        int result = orderMapper.direct(paymentNumber);

        if (result > 0) {
            PaymentDTO paymentDTO = paymentMapper.getPaymentInfo(paymentNumber);
            int result1 = productMapper.reductionAfterPayment(paymentDTO); // 상품정보에서 개수 차감하는 메서드

            if(result1 >0){
                JSFunc.alertLocation("구매 수락/상품 개수 차감.","/es/mypage/orderList_1?pageNo="+pageNo, response);
            } else{
                JSFunc.alertLocation("구매 수락/상품 개수 차감 오류. 수정 필요","/es/mypage/orderList_1?pageNo="+pageNo, response);
            }
        } else {
            JSFunc.alertBack("구매 수락 실패", response);
        }
        return null;
    }



}
