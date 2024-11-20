package net.fullstack7.edusecond.edusecond.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductRegistDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductImageDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductUpdateDTO;
import net.fullstack7.edusecond.edusecond.dto.review.ReviewDTO;
import net.fullstack7.edusecond.edusecond.dto.seller.SellerDTO;
import net.fullstack7.edusecond.edusecond.dto.seller.StarAvgDTO;
import net.fullstack7.edusecond.edusecond.mapper.SellerMapper;
import net.fullstack7.edusecond.edusecond.service.Like.LikeServiceIf;
import net.fullstack7.edusecond.edusecond.service.member.MemberServiceIf;
import net.fullstack7.edusecond.edusecond.service.product.ProductServiceIf;
import net.fullstack7.edusecond.edusecond.service.review.ReviewServiceIf;
import net.fullstack7.edusecond.edusecond.util.CommonDateUtil;
import net.fullstack7.edusecond.edusecond.util.CommonFileUtil;
import net.fullstack7.edusecond.edusecond.util.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.fullstack7.edusecond.edusecond.util.JSFunc;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductServiceIf productService;
    private final LikeServiceIf likeService;
    private final ReviewServiceIf reviewService;
    private final SellerMapper sellerMapper;
    private final MemberServiceIf MemberService;
    @GetMapping("/list")
    public String list(Model model,
                  @RequestParam(defaultValue = "1") int pageNo,
                  @RequestParam(required = false) String searchCategory,
                  @RequestParam(required = false) String searchValue,
                  HttpServletResponse response) {
        try {
            response.setCharacterEncoding("utf-8");
            if (!validateListParameters(pageNo, searchCategory, searchValue, response)) {
                return null;
            }
            List<ProductDTO> pList = productService.list(pageNo, 10, 5, searchCategory, searchValue, "AVAILABLE");

            if (pList == null || pList.isEmpty()) {
                model.addAttribute("message", "등록된 상품이 없습니다.");
                model.addAttribute("searchCategory", searchCategory);
                model.addAttribute("searchValue", searchValue);
                log.info("Message attribute set: " + model.getAttribute("message"));
                return "product/list";
            }

            for (ProductDTO dto : pList) {
                String formatDate = CommonDateUtil.localDateTimeToString(dto.getRegDate(), "yyyy-MM-dd");
                dto.setFormatRegDate(formatDate);
            }
            int totalCount = productService.totalCount(searchCategory, searchValue, "AVAILABLE");
            Paging paging = new Paging(pageNo, 10, 5, totalCount);

            model.addAttribute("pList", pList);
            model.addAttribute("paging", paging);
            model.addAttribute("searchCategory", searchCategory);
            model.addAttribute("searchValue", searchValue);

            return "product/list";
        } catch (Exception e) {
            log.error("상품 목록 조회 중 오류 발생: ", e);
            JSFunc.alertBack("상품 목록을 불러오는 중 오류가 발생했습니다.", response);
            return null;
        }
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

    @GetMapping("/view")
    public String view(Model model,
                      HttpSession session,
                      @RequestParam int productId,
                      HttpServletResponse response,
                      HttpServletRequest request) {
        MemberLoginDTO userDto = (MemberLoginDTO) session.getAttribute("memberInfo");

        response.setCharacterEncoding("utf-8");
        try {
            if (productId <= 0) {
                JSFunc.alertBack("유효하지 않은 상품 ID입니다.", response);
                return null;
            }
            ProductDTO dto = productService.view(productId);
            if (dto == null) {
                JSFunc.alertBack("존재하지 않는 상품입니다.", response);
                return null;
            }if(dto.getProductStatus().equals("UNAVAILABLE")){
                JSFunc.alertBack("현재 판매중인 상품이 아닙니다.", response);
                return null;
            }else {
                // 로그인한 사용자일 경우에만 조회수 증가 처리
                if (userDto != null) {
                    String viewedProducts = null;
                    Cookie[] cookies = request.getCookies();
                    
                    // 기존 쿠키 확인
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("viewedProducts_" + userDto.getUserId())) {
                                viewedProducts = cookie.getValue();
                                break;
                            }
                        }
                    }
                    
                    // 해당 상품 조회 여부 확인
                    if (viewedProducts == null || !viewedProducts.contains("[" + productId + "]")) {
                        // 조회수 증가
                        if(!dto.getSellerId().equals(userDto.getUserId())){
                            log.info("조회수 증가");
                            productService.updateViewCount(productId);
                        }else{
                            log.info("본인 상품 조회수 증가 안함");
                        }
                        // 쿠키 생성 또는 업데이트
                        String newValue = (viewedProducts == null) ? 
                            "[" + productId + "]" : 
                            viewedProducts + "[" + productId + "]";
                            
                        Cookie viewCookie = new Cookie("viewedProducts_" + userDto.getUserId(), newValue);
                        viewCookie.setMaxAge(24 * 60 * 60); // 24시간
                        viewCookie.setPath("/");
                        response.addCookie(viewCookie);
                    }
                }

                List<ReviewDTO> reviewList = reviewService.viewReview(productId);
                if(reviewList != null) {
                    for (ReviewDTO i : reviewList) {
                        String formatDate = CommonDateUtil.localDateTimeToString(i.getRegDate(), "yyyy-MM-dd");
                        i.setFormatRegDate(formatDate);
                    }
                    model.addAttribute("reviewList", reviewList);
                }

                boolean isLiked = false;
                if(userDto != null && userDto.getUserId() != null) {
                    model.addAttribute("userId", userDto.getUserId());
                    isLiked = likeService.checkExists(userDto.getUserId(), productId);
                }
                
                model.addAttribute("isLiked", isLiked);
                model.addAttribute("dto", dto);
                return "product/view";
            }
        } catch (Exception e) {
            log.error("상품 상세 조회 중 오류 발생: ", e);
            JSFunc.alertBack("상품 정보를 불러오는 중 오류가 발생했습니다.", response);
            return null;
        }
    }


    @GetMapping("/es/pay")
    public String payment(Model model,
                          @RequestParam int productId,
                          HttpServletResponse response){
        try{
            response.setCharacterEncoding("utf-8");
            if (productId <= 0) {
                JSFunc.alertBack("유효하지 않은 상품 ID입니다.", response);
                return null;
            }
            ProductDTO dto = productService.view(productId);
            if (dto == null) {
                JSFunc.alertBack("존재하지 않는 상품입니다.", response);
                return null;
            }
            model.addAttribute("dto", dto);
            return "product/pay";
        }catch(Exception e){
            log.error("상품 결제창 중 오류 발생: ", e);
            JSFunc.alertBack("상품 결제창을 불러오는 중 오류가 발생했습니다.", response);
            return null;
        }

    }
    @GetMapping("/regist")
    public String regist(HttpSession session, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        
        if(memberLoginDTO == null || memberLoginDTO.getUserId() == null){
            JSFunc.alertBack("로그인한 회원만 상품 등록이 가능합니다.", response);
            return null;
        }
        return "product/regist";
    }


    @PostMapping("/registOk")
    public String registOk(
            HttpSession session,
            @Valid ProductRegistDTO productRegistDTO,
            BindingResult bindingResult,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response
    ) throws IOException {
        response.setCharacterEncoding("utf-8");
        log.info("파일 업로드 시작");
        log.info("- 파일 개수: {}", files.size());
        log.info("- 첫번째 파일 정보: {}", files.get(0).getOriginalFilename());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            log.info("RegistOk에서 Validation error");
            return "redirect:/product/regist";
        }

        if (files == null || files.isEmpty() || files.stream().allMatch(MultipartFile::isEmpty)) {
            redirectAttributes.addFlashAttribute("errorMessage", "상품 사진은 필수입니다.");
            log.info("파일이 비어 있습니다. 상품 사진은 필수입니다.");
            return "redirect:/product/regist";
        }

        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
        productRegistDTO.setSellerId(memberLoginDTO.getUserId());

        int result = productService.insertProduct(productRegistDTO); // 상품 삽입

        if (result > 0) { // 상품이 정상적으로 등록되었을 때
            // 파일이 있는 경우에만 업로드 및 DB 삽입 수행
            if (files != null && !files.isEmpty()) {
                log.info(files.toString());
                List<String> uploadFileNames = CommonFileUtil.uploadFiles(files); // 파일 로드 완료
                int lastProductId = productService.getLastProductId(); // 마지막에 삽입한 ProductId 가져오기
                productService.insertProductImage(lastProductId, uploadFileNames); // 파일 정보를 DB에 삽입
            } else {
                JSFunc.alertBack("상품 사진은 필수입니다.", response);
            }
            log.info("[ProductController] >> registOk [SUCCESS] >> /product/list");
            return "redirect:/product/list"; // 성공 시 리다이렉트
        } else {
            // 상품 테이블에 등록 실패 시
            log.info("[ProductController] >> registOk [FAIL] >> /product/list");
            return "redirect:/product/regist";
        }
    }

    @GetMapping("seller/sellerpage")
    public String intro(
            @RequestParam String userId
            , RedirectAttributes redirectAttributes
            , Model model
    ){
        try{
            MemberDTO result = MemberService.getMember(userId);
            model.addAttribute("member", result);
            List<SellerDTO> list = sellerMapper.selectProductInfo(userId);
            model.addAttribute("list", list);
            List<SellerDTO> ReviewList = sellerMapper.selectReviewInfo(userId);
            model.addAttribute("ReviewList", ReviewList);
            StarAvgDTO StarAvg = sellerMapper.selectReviewStar(userId);
            model.addAttribute("StarAvg", StarAvg);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "product/seller/sellerpage";
    }

    @GetMapping("delete")
    public String delete(
            @RequestParam int productId,
            HttpServletResponse response,
            Model model,
            HttpSession session
    ){
        try{
            response.setCharacterEncoding("utf-8");
            MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
            if(memberLoginDTO == null || memberLoginDTO.getUserId() == null){
                JSFunc.alertBack("로그인한 회원만 삭제할 수 있습니다.", response);
                return null;
            }else if(memberLoginDTO.getUserId().equals(productService.view(productId).getSellerId())){
                productService.deleteProduct(productId);
                return "redirect:/product/list";
            }else{
                JSFunc.alertBack("자신의 상품만 삭제할 수 있습니다.", response);
                return null;
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("update")
    public String update(
            @RequestParam int productId,
            Model model,
            HttpSession session,
            HttpServletResponse response
    ) {
        try {
            response.setCharacterEncoding("utf-8");
            // 로그인 체크
            MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
            if(memberLoginDTO == null || memberLoginDTO.getUserId() == null) {
                JSFunc.alertBack("로그인한 회원만 수정할 수 있습니다.", response);
                return null;
            }
            
            // 상품 정보 조회
            ProductDTO dto = productService.view(productId);
            if(dto == null) {
                JSFunc.alertBack("존재하지 않는 상품입니다.", response);
                return null;
            }
            
            // 본인 상품 체크
            if(!memberLoginDTO.getUserId().equals(dto.getSellerId())) {
                JSFunc.alertBack("자신의 상품만 수정할 수 있습니다.", response);
                return null;
            }
            
            // 상품 이미지 조회
            List<ProductImageDTO> images = productService.getProductImages(productId);
            
            model.addAttribute("dto", dto);
            model.addAttribute("images", images);
            return "product/update";
        } catch(Exception e) {
            log.error("상품 수정 페이지 로드 중 오류 발생: ", e);
            JSFunc.alertBack("상품 수정 페이지를 불러오는 중 오류가 발생했습니다.", response);
            return null;
        }
    }

    @PostMapping("/updateOk")
    public String updateOk(
            ProductUpdateDTO productUpdateDTO,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam String imageUpdateType,
            HttpSession session,
            HttpServletResponse response
    ) {
        try {
            response.setCharacterEncoding("utf-8");
            
            // 1. 기본 데이터 검증
            if (productUpdateDTO.getProductId() == null || 
                productUpdateDTO.getProductName() == null || productUpdateDTO.getProductName().trim().isEmpty() ||
                productUpdateDTO.getProductDesc() == null || productUpdateDTO.getProductDesc().trim().isEmpty() ||
                productUpdateDTO.getPrice() == null || productUpdateDTO.getPrice() < 0 ||
                productUpdateDTO.getQuantity() == null || productUpdateDTO.getQuantity() < 0) {
                JSFunc.alertBack("필수 입력값이 누락되었습니다.", response);
                return null;
            }

            // 2. quality와 productStatus 검증
            String quality = productUpdateDTO.getQuality();
            String productStatus = productUpdateDTO.getProductStatus();
            if (quality == null || !quality.matches("^(NEW|LIKE_NEW|GOOD|FAIR)$") ||
                productStatus == null || !productStatus.matches("^(AVAILABLE|RESERVED|SOLD)$")) {
                JSFunc.alertBack("올바른 상품 상태를 선택해주세요.", response);
                return null;
            }

            // 3. 로그인 체크
            MemberLoginDTO memberLoginDTO = (MemberLoginDTO) session.getAttribute("memberInfo");
            if (memberLoginDTO == null) {
                JSFunc.alertBack("로그인이 필요합니다.", response);
                return null;
            }

            // 4. 상품 소유자 체크
            ProductDTO originalProduct = productService.view(productUpdateDTO.getProductId());
            if (!memberLoginDTO.getUserId().equals(originalProduct.getSellerId())) {
                JSFunc.alertBack("자신의 상품만 수정할 수 있습니다.", response);
                return null;
            }

            // imageUpdateType을 DTO에 설정
            productUpdateDTO.setImageUpdateType(imageUpdateType);

            // 이미지 업데이트 타입이 'update'인 경우에만 파일 검증
            if ("update".equals(imageUpdateType)) {
                if (files == null || files.isEmpty() || files.size() > 4) {
                    JSFunc.alertBack("1~4개의 이미지를 선택해주세요.", response);
                    return null;
                }
                
                for (MultipartFile file : files) {
                    if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
                        JSFunc.alertBack("올바른 이미지 파일을 선택해주세요.", response);
                        return null;
                    }
                }
            }

            int result = productService.updateProduct(productUpdateDTO, files);
            
            if (result > 0) {
                return "redirect:/product/view?productId=" + productUpdateDTO.getProductId();
            } else {
                JSFunc.alertBack("상품 수정에 실패했습니다.", response);
                return null;
            }
        } catch (Exception e) {
            log.error("상품 수정 중 오류 발생: ", e);
            JSFunc.alertBack("상품 수정 중 오류가 발생했습니다.", response);
            return null;
        }
    }

}
