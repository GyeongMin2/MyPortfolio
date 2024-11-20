package net.fullstack7.edusecond.edusecond.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.order.OrderListDTO;
import net.fullstack7.edusecond.edusecond.dto.review.ReviewDTO;
import net.fullstack7.edusecond.edusecond.mapper.OrderMapper;
import net.fullstack7.edusecond.edusecond.mapper.ReviewMapper;
import net.fullstack7.edusecond.edusecond.service.review.ReviewServiceIf;
import net.fullstack7.edusecond.edusecond.util.JSFunc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/es/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
    private final ReviewServiceIf reviewService;
    private final OrderMapper orderMapper;
    @GetMapping("/review")
    public String review(Model model, HttpSession session, @RequestParam("orderId") int orderId) {
        OrderListDTO orderListDTO = orderMapper.getOrder(orderId);
        model.addAttribute("orderListDTO", orderListDTO);
        return "mypage/review";
    }

    @PostMapping("/reviewOk")
    public String reviewOk(Model model, HttpSession session, ReviewDTO reviewDTO, HttpServletResponse response) {

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        reviewDTO.setWriterId(memberLoginDTO.getUserId());
        log.info(reviewDTO);
        int result = reviewService.insertReview(reviewDTO);
        response.setCharacterEncoding("utf-8");
        if(result > 0){
            JSFunc.alertLocation("리뷰 작성 성공", "/es/mypage/orderList",response);
            return null;
        } else{
            JSFunc.alertBack("리뷰 작성 성공",response);
            return null;
        }
    }
}
