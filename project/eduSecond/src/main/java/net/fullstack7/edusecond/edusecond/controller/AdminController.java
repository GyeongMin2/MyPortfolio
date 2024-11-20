package net.fullstack7.edusecond.edusecond.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.AdminLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductDTO;
import net.fullstack7.edusecond.edusecond.dto.product.ProductImageDTO;
import net.fullstack7.edusecond.edusecond.service.admin.AdminServiceIf;
import net.fullstack7.edusecond.edusecond.service.member.MemberServiceIf;
import net.fullstack7.edusecond.edusecond.service.product.ProductServiceIf;
import net.fullstack7.edusecond.edusecond.util.Paging;
import net.fullstack7.edusecond.edusecond.service.notice.NoticeServiceIf;
import net.fullstack7.edusecond.edusecond.dto.notice.NoticeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminController {
    private final AdminServiceIf adminService;
    private final ProductServiceIf productService;
    private final MemberServiceIf memberService;
    private final NoticeServiceIf noticeService;
    
    @GetMapping("/login")
    public String loginPage() {
        return "admin/adminLogin";
    }
    
    @PostMapping("/login")
    public String loginProcess(AdminLoginDTO loginDTO, 
                             HttpSession session,
                             Model model) {
        if(adminService.loginAdmin(loginDTO)) {
            session.setAttribute("adminId", loginDTO.getAdminId());
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        return "admin/adminLogin";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("productCount", productService.totalCount(null, null, ""));
        model.addAttribute("memberCount", memberService.getTotalCount(null, null));
        return "admin/dashboard";
    }

    @GetMapping("/member/list")
    public String memberList(@RequestParam(defaultValue = "1") int pageNo,
                           @RequestParam(required = false) String searchCategory,
                           @RequestParam(required = false) String searchValue,
                           Model model) {
        int totalCount = memberService.getTotalCount(searchCategory, searchValue);
        Paging paging = new Paging(pageNo, 10, 5, totalCount);

        List<MemberDTO> members = memberService.getList(pageNo, 10, searchCategory, searchValue);

        model.addAttribute("members", members);
        model.addAttribute("paging", paging);
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchValue", searchValue);

        return "admin/member/list";
    }

    @GetMapping("/member/status")
    public String updateMemberStatus(
            @RequestParam String userId,
            @RequestParam String enabled,
            RedirectAttributes rttr) {
        
        if(memberService.updateEnabled(userId, enabled)) {
            rttr.addFlashAttribute("message", "회원 상태가 변경되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "회원 상태 변경에 실패했습니다.");
        }
        return "redirect:/admin/member/list";
    }

    @GetMapping("/member/delete")
    public String deleteMember(@RequestParam String userId,
                             RedirectAttributes rttr) {
        if(memberService.deleteMember(userId)) {
            rttr.addFlashAttribute("message", "회원이 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "회원 삭제에 실패했습니다.");
        }
        return "redirect:/admin/member/list";
    }

    @GetMapping("/product/list")
    public String productList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String searchCategory,
            @RequestParam(required = false) String searchValue,
            Model model) {
        try {
            List<ProductDTO> products = productService.list(pageNo, pageSize, 5, searchCategory, searchValue, "");
            int totalCount = productService.totalCount(searchCategory, searchValue, "");
            Paging paging = new Paging(pageNo, pageSize, 5, totalCount);

            model.addAttribute("products", products);
            model.addAttribute("paging", paging);
            model.addAttribute("searchCategory", searchCategory);
            model.addAttribute("searchValue", searchValue);

            return "admin/product/list";
        } catch (Exception e) {
            log.error("상품 목록 조회 중 오류 발생: ", e);
            return "redirect:/admin/dashboard";
        }
    }

    @GetMapping("/product/view")
    public String productView(@RequestParam int productId, Model model) {
        try {
            ProductDTO product = productService.view(productId);
            if (product == null) {
                return "redirect:/admin/product/list";
            }

            List<ProductImageDTO> images = productService.getProductImages(productId);
            product.setImages(images);

            model.addAttribute("product", product);
            return "admin/product/view";
        } catch (Exception e) {
            log.error("상품 상세 조회 중 오류 발생: ", e);
            return "redirect:/admin/product/list";
        }
    }
    
    @GetMapping("/product/delete")
    public String productDelete(@RequestParam int productId, RedirectAttributes rttr) {
        try{
            productService.deleteProduct(productId);
            rttr.addFlashAttribute("message", "상품이 삭제되었습니다.");
        } catch (Exception e) {
            log.error("상품 삭제 중 오류 발생: ", e);
            rttr.addFlashAttribute("error", "상품 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/admin/product/list";
    }
    
    @GetMapping("/notice/list")
    public String noticeList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String searchCategory,
            @RequestParam(required = false) String searchValue,
            Model model) {
        try {
            List<NoticeDTO> notices = noticeService.getList(pageNo, pageSize, searchCategory, searchValue);
            int totalCount = noticeService.getTotalCount(searchCategory, searchValue);
            Paging paging = new Paging(pageNo, pageSize, 5, totalCount);
            
            model.addAttribute("notices", notices);
            model.addAttribute("paging", paging);
            model.addAttribute("searchCategory", searchCategory);
            model.addAttribute("searchValue", searchValue);
            
            return "admin/notice/list";
        } catch (Exception e) {
            log.error("공지사항 목록 조회 중 오류 발생: ", e);
            return "redirect:/admin/dashboard";
        }
    }
    
    @GetMapping("/notice/view")
    public String noticeView(@RequestParam int noticeId, Model model) {
        try {
            NoticeDTO notice = noticeService.getNotice(noticeId);
            if (notice == null) {
                return "redirect:/admin/notice/list";
            }
            model.addAttribute("notice", notice);
            return "admin/notice/view";
        } catch (Exception e) {
            log.error("공지사항 상세 조회 중 오류 발생: ", e);
            return "redirect:/admin/notice/list";
        }
    }
    
    @GetMapping("/notice/register")
    public String noticeRegisterForm() {
        return "admin/notice/register";
    }
    
    @PostMapping("/notice/register")
    public String noticeRegister(NoticeDTO notice, HttpSession session, RedirectAttributes rttr) {
        try {
            notice.setAdminId((String) session.getAttribute("adminId"));
            if (noticeService.create(notice)) {
                rttr.addFlashAttribute("message", "공지사항이 등록되었습니다.");
                return "redirect:/admin/notice/list";
            }
            rttr.addFlashAttribute("error", "공지사항 등록에 실패했습니다.");
            return "redirect:/admin/notice/register";
        } catch (Exception e) {
            log.error("공지사항 등록 중 오류 발생: ", e);
            rttr.addFlashAttribute("error", "공지사항 등록 중 오류가 발생했습니다.");
            return "redirect:/admin/notice/register";
        }
    }
    
    @GetMapping("/notice/modify")
    public String noticeModifyForm(@RequestParam int noticeId, Model model) {
        NoticeDTO notice = noticeService.getNotice(noticeId);
        if (notice == null) {
            return "redirect:/admin/notice/list";
        }
        model.addAttribute("notice", notice);
        return "admin/notice/modify";
    }
    
    @PostMapping("/notice/modify")
    public String noticeModify(NoticeDTO notice, RedirectAttributes rttr) {
        try {
            if (noticeService.update(notice)) {
                rttr.addFlashAttribute("message", "공지사항이 수정되었습니다.");
                return "redirect:/admin/notice/view?noticeId=" + notice.getNoticeId();
            }
            rttr.addFlashAttribute("error", "공지사항 수정에 실패했습니다.");
            return "redirect:/admin/notice/modify?noticeId=" + notice.getNoticeId();
        } catch (Exception e) {
            log.error("공지사항 수정 중 오류 발생: ", e);
            rttr.addFlashAttribute("error", "공지사항 수정 중 오류가 발생했습니다.");
            return "redirect:/admin/notice/modify?noticeId=" + notice.getNoticeId();
        }
    }
    
    @GetMapping("/notice/delete")
    public String noticeDelete(@RequestParam int noticeId, RedirectAttributes rttr) {
        if (noticeService.delete(noticeId)) {
            rttr.addFlashAttribute("message", "공지사항이 삭제되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "공지사항 삭제에 실패했습니다.");
        }
        return "redirect:/admin/notice/list";
    }

    @GetMapping("/member/withdrawal/list")
    public String withdrawalList(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(required = false) String searchCategory,
            @RequestParam(required = false) String searchValue,
            Model model) {
        
        int pageSize = 10;
        List<MemberDTO> members = memberService.getWithdrawalList(pageNo, pageSize, searchCategory, searchValue);
        int totalCount = memberService.getWithdrawalTotalCount(searchCategory, searchValue);
        
        model.addAttribute("members", members);
        model.addAttribute("paging", new Paging(pageNo, pageSize, 5, totalCount));
        model.addAttribute("searchCategory", searchCategory);
        model.addAttribute("searchValue", searchValue);
        
        return "admin/member/withdrawalList";
    }

    @PostMapping("/member/withdrawal/process")
    public String processWithdrawal(
            @RequestParam String userId,
            @RequestParam String action,
            RedirectAttributes rttr) {
        
        if ("approve".equals(action)) {
            if (memberService.processWithdrawal(userId)) {
                rttr.addFlashAttribute("message", "회원 탈퇴가 승인되었습니다.");
            } else {
                rttr.addFlashAttribute("error", "탈퇴 처리 중 오류가 발생했습니다.");
            }
        } else if ("reject".equals(action)) {
            if (memberService.updateEnabled(userId, "Y")) {
                rttr.addFlashAttribute("message", "회원 탈퇴가 거절되었습니다.");
            } else {
                rttr.addFlashAttribute("error", "탈퇴 거절 처리 중 오류가 발생했습니다.");
            }
        }
        
        return "redirect:/admin/member/withdrawal/list";
    }
}
