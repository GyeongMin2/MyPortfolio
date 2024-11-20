package net.fullstack7.edusecond.edusecond.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberModifyDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MypageDTO;
import net.fullstack7.edusecond.edusecond.dto.order.OrderListDTO;
import net.fullstack7.edusecond.edusecond.mapper.MemberMapper;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import net.fullstack7.edusecond.edusecond.mapper.ReviewMapper;
import net.fullstack7.edusecond.edusecond.service.Like.LikeServiceIf;
import net.fullstack7.edusecond.edusecond.mapper.OrderMapper;
import net.fullstack7.edusecond.edusecond.service.member.MemberServiceIf;
import net.fullstack7.edusecond.edusecond.service.product.ProductServiceIf;
import net.fullstack7.edusecond.edusecond.util.CommonDateUtil;
import net.fullstack7.edusecond.edusecond.util.JSFunc;
import net.fullstack7.edusecond.edusecond.util.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/es/mypage")
@RequiredArgsConstructor
@Log4j2
public class MypageController {
    private final MemberServiceIf memberService;
    private final MemberMapper memberMapper;
    private final ProductServiceIf productService;
    private final OrderMapper orderMapper;
    private final ReviewMapper reviewMapper;


    @GetMapping("/myInfo")
    public String myInfo(HttpSession session, Model model,HttpServletResponse response) {

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        response.setCharacterEncoding("UTF-8");
        if(memberLoginDTO == null) {
            JSFunc.alertBack("로그인 후 이용 가능합니다.",response);
        }

        MypageDTO mypageDTO = memberMapper.myProductCount(memberLoginDTO.getUserId());
        MemberDTO memberDTO = memberService.getMember(memberLoginDTO.getUserId());

        model.addAttribute("dUtil", new CommonDateUtil());
        model.addAttribute("mypageDTO", mypageDTO);
        model.addAttribute("member", memberDTO);
        return "mypage/myInfo";
    }

    @GetMapping("/modify")
    public String modify(HttpSession session, Model model, HttpServletResponse response) {
        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        response.setCharacterEncoding("UTF-8");
        if(memberLoginDTO == null) {
            JSFunc.alertBack("로그인 후 이용 가능합니다.",response);
        }

        model.addAttribute("dUtil", new CommonDateUtil());
        model.addAttribute("member", memberService.getMember(memberLoginDTO.getUserId())); //예시임. 바꿔줘야함
        return "mypage/modify";
    }

    @PostMapping("/modifyOk")
    public String modifyOk(HttpSession session,
                           @Valid MemberModifyDTO memberModifyDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            return "redirect:/es/mypage/modify";
        }
        memberModifyDTO.setUserId(memberLoginDTO.getUserId());  //예시임. 바꿔줘야 함
        int result = memberService.modifyMember(memberModifyDTO);
        if(result > 0){
            return "redirect:/es/mypage/myInfo";
        } else{
            log.info("수정실패");
            return "redirect:/es/mypage/modify";
        }
    }

    @GetMapping("/delete")
    public String delete(HttpSession session, RedirectAttributes rttr) {
        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        if (memberService.requestWithdrawal(memberLoginDTO.getUserId())) {
            session.invalidate(); // 세션 무효화
            rttr.addFlashAttribute("message", "탈퇴 신청이 완료되었습니다. 관리자 승인 후 처리됩니다.");
            return "redirect:/main/main";
        } else {
            rttr.addFlashAttribute("error", "탈퇴 신청 처리 중 오류가 발생했습니다.");
            return "redirect:/es/mypage/myInfo";
        }
    }

    @GetMapping("/deleteCancel")
    public String deleteCancel(HttpSession session, RedirectAttributes rttr) {
        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        if (memberLoginDTO == null) {
            rttr.addFlashAttribute("error", "로그인이 필요합니다.");
            return "redirect:/main/main";
        }

        try {
            int result = memberMapper.updateEnabled(memberLoginDTO.getUserId(), "Y");
            if (result > 0) {
                rttr.addFlashAttribute("message", "탈퇴 신청이 취소되었습니다.");
            } else {
                rttr.addFlashAttribute("error", "탈퇴 신청 취소 처리 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            log.error("탈퇴 신청 취소 중 오류 발생: ", e);
            rttr.addFlashAttribute("error", "탈퇴 신청 취소 처리 중 오류가 발생했습니다.");
        }

        return "redirect:/es/mypage/myInfo";
    }

    private boolean validateListParameters(int pageNo, String searchCategory,
                                           String searchValue, HttpServletResponse response) {
        if (pageNo < 1) {
            JSFunc.alertBack("페이지 번호는 1 이상이어야 합니다.", response);
            return false;
        }

        if (searchCategory != null && !searchCategory.trim().isEmpty()
                && searchValue != null && !searchValue.trim().isEmpty()) {
            if (!("productName".equals(searchCategory) || "sellerId".equals(searchCategory))) {
                JSFunc.alertBack("유효하지 않은 검색 카테고리입니다: " + searchCategory, response);
                return false;
            }
        }
        return true;
    }

    @GetMapping("/wishList")
    public String viewWishList(Model model,
                               HttpSession session,
                               @RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(required = false) String searchCategory,
                               @RequestParam(required = false) String searchValue,
                               HttpServletResponse response){
        MemberLoginDTO loginDto = (MemberLoginDTO) session.getAttribute("memberInfo");
        try{
            if(!validateListParameters(pageNo, searchCategory, searchValue, response)){
                return null;
            }
            List<ProductDTO> pList = productService.selectAllByUser(pageNo, 10, 5, searchCategory, searchValue, loginDto.getUserId(), "wish");
            int totalCount = productService.totalCountLikedProducts(searchCategory, searchValue, loginDto.getUserId());
            log.info("totalCount: " + totalCount);
            Paging paging = new Paging(pageNo, 10, 5, totalCount);
            model.addAttribute("pList", pList);
            model.addAttribute("paging", paging);
            model.addAttribute("searchCategory", searchCategory);
            model.addAttribute("searchValue", searchValue);
            log.info("pList.size()" + pList.size());
            return "/mypage/wishList";
        }catch(Exception e){
            log.error("찜 목록 조회 중 오류 발생" + e.getMessage());
            return null;
        }
    }

    @GetMapping("/orderList")
    public String viewOrderList(Model model, HttpSession session,
                                @RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(required = false) String searchValue,
                                HttpServletResponse response){

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        String userId = memberLoginDTO.getUserId();
        Map<String,Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("offset", (pageNo - 1) * 10);
        map.put("limit", 10);
        map.put("searchCategory", "p.productName");
        map.put("searchValue", searchValue);
        log.info("searchValue : "+searchValue);

        Map<String,Object> countMap = new HashMap<>();
        countMap.put("searchCategory", "p.productName");
        countMap.put("searchValue", searchValue);
        countMap.put("userId", userId);

        List<OrderListDTO> orderList = orderMapper.getOrderList(map);
        int totalCount = orderMapper.totalCount(countMap);
        log.info("totalcount : "+totalCount);
        Paging paging = new Paging(pageNo, 10, 5, totalCount);

        model.addAttribute("dUtil", new CommonDateUtil());
        model.addAttribute("paging", paging);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("orderList", orderList);
        return "/mypage/orderList";
    }


    @GetMapping("/orderList_1")
    public String viewOrderList_1(Model model, HttpSession session,
                                  @RequestParam(defaultValue = "1") int pageNo,
                                  @RequestParam(required = false) String searchValue,
                                  HttpServletResponse response){

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        String userId = memberLoginDTO.getUserId();
        Map<String,Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("offset", (pageNo - 1) * 10);
        map.put("limit", 10);
        map.put("searchCategory", "p.productName");
        map.put("searchValue", searchValue);
        log.info(searchValue);

        Map<String,Object> countMap = new HashMap<>();
        countMap.put("searchCategory", "p.productName");
        countMap.put("searchValue", searchValue);
        countMap.put("userId", userId);

        List<OrderListDTO> orderList = orderMapper.getOrderListSold(map);

        int totalCount = orderMapper.totalCountSold(countMap);
        log.info(totalCount);
        Paging paging = new Paging(pageNo, 10, 5, totalCount);

        model.addAttribute("dUtil", new CommonDateUtil());
        model.addAttribute("paging", paging);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("orderList", orderList);
        return "/mypage/orderListSold";

    }




    @GetMapping("/productList")
    public String viewMyProduct(Model model,
                               HttpSession session,
                               @RequestParam(defaultValue = "1") int pageNo,
                               @RequestParam(required = false) String searchCategory,
                               @RequestParam(required = false) String searchValue,
                               HttpServletResponse response){
        MemberLoginDTO loginDto = (MemberLoginDTO) session.getAttribute("memberInfo");
        try{
            if(!validateListParameters(pageNo, searchCategory, searchValue, response)){
                return null;
            }
            List<ProductDTO> availableList  = productService.selectAllByProductStatus(pageNo, 10, 5, searchCategory, searchValue, loginDto.getUserId(), "AVAILABLE");

            int availableTotalCount = productService.totalCountByProductStatus(searchCategory, searchValue, loginDto.getUserId(), "AVAILABLE");

            Paging availablePaging = new Paging(pageNo, 10, 5, availableTotalCount);

            model.addAttribute("availableList", availableList);
            model.addAttribute("paging", availablePaging);


            model.addAttribute("searchCategory", searchCategory);
            model.addAttribute("searchValue", searchValue);
            return "/mypage/productList";
        }catch(Exception e){
            log.error("내 상품 조회 중 오류 발생" + e.getMessage());
            return null;
        }
    }

    @GetMapping("/productList_1")
    public String viewMyProduct1(Model model,
                                HttpSession session,
                                @RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(required = false) String searchCategory,
                                @RequestParam(required = false) String searchValue,
                                HttpServletResponse response){
        MemberLoginDTO loginDto = (MemberLoginDTO) session.getAttribute("memberInfo");
        try{
            if(!validateListParameters(pageNo, searchCategory, searchValue, response)){
                return null;
            }
            List<ProductDTO> soldOutList  = productService.selectAllByProductStatus(pageNo, 10, 5, searchCategory, searchValue, loginDto.getUserId(), "SOLDOUT");
            int soldOutTotalCount = productService.totalCountByProductStatus(searchCategory, searchValue, loginDto.getUserId(), "SOLDOUT");
            Paging soldOutPaging = new Paging(pageNo, 10, 5, soldOutTotalCount);

            model.addAttribute("soldOutList", soldOutList);
            model.addAttribute("paging", soldOutPaging);

            model.addAttribute("searchCategory", searchCategory);
            model.addAttribute("searchValue", searchValue);
            return "/mypage/productList_1";
        }catch(Exception e){
            log.error("내 상품 조회 중 오류 발생" + e.getMessage());
            return null;
        }
    }


}
