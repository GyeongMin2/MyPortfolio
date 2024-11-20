package net.fullstack7.edusecond.edusecond.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.product.LikeDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import net.fullstack7.edusecond.edusecond.service.Like.LikeServiceIf;
import net.fullstack7.edusecond.edusecond.service.product.ProductServiceIf;
import net.fullstack7.edusecond.edusecond.util.JSFunc;
import net.fullstack7.edusecond.edusecond.util.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/es/like")
@Log4j2
@RequiredArgsConstructor
public class LikeController {
    private final LikeServiceIf likeService;
    private final ProductServiceIf productService;

    @GetMapping("/insert")
    public String insertLike(Model model,
                             @RequestParam String userId,
                             @RequestParam Integer productId,
                             HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        try{
            if(userId != null && !userId.trim().isEmpty()){
                likeService.insertLike(userId, productId);
            }else{
                JSFunc.alertBack("로그인 후 이용해주세요",response);
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/product/view?productId=" + productId;
    }


    @GetMapping("/delete")
    public String deleteLike(Model model,
                             HttpSession session,
                             @RequestParam Integer productId,
                             @RequestParam(required = false) String source){
        MemberLoginDTO loginDto = (MemberLoginDTO) session.getAttribute("memberInfo");
        try{
            likeService.deleteLike(loginDto.getUserId(), productId);
        }catch(Exception e){
            log.error("좋아요 수정 실패:" + e.getMessage());
        }

        if ("mypage".equals(source)) {
            return "redirect:/es/mypage/wishList";
        } else {
            model.addAttribute("isLike", "false");
            return "redirect:/product/view?productId=" + productId;
        }
    }
}
