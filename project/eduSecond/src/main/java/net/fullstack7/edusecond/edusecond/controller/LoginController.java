package net.fullstack7.edusecond.edusecond.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.member.MemberDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberLoginDTO;
import net.fullstack7.edusecond.edusecond.dto.member.MemberRegistDTO;
import net.fullstack7.edusecond.edusecond.service.member.MemberServiceIf;
import net.fullstack7.edusecond.edusecond.service.member.MemberServiceImpl;
import net.fullstack7.edusecond.edusecond.util.CommonUtil;
import net.fullstack7.edusecond.edusecond.util.JSFunc;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;


@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Log4j2
public class LoginController extends HttpServlet {
    private final MemberServiceIf MemberService;
    private CommonUtil cUtil = new CommonUtil();
    private JSFunc JSFunc = new JSFunc();


    @GetMapping("/agreeregist")
    public String agreeregist(){
        return "login/agreeregist";
    }
    @GetMapping("/login")
    public String login(){
        return "login/login";
    }
    @GetMapping("/regist")
    public String regist(){
        return "login/regist";
    }

    @PostMapping("/login")
    public String loginOk (
             @RequestParam String userId
            , @RequestParam String password
            ,HttpSession session,
             RedirectAttributes redirectAttributes
    ){

        try{
            boolean result = MemberService.loginMember(userId, password);
            if(result){
                MemberLoginDTO mdto = MemberService.getLoginMember(userId);
                session.setAttribute("memberInfo", mdto);
                return "redirect:/main/main";
            } else{
                redirectAttributes.addFlashAttribute("errorMessage", "존재하지 않는 계정입니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("============================================");
        return "redirect:/login/login";
    }

    @PostMapping("/regist")
    public String registOk(
            @Valid MemberRegistDTO memberRegistDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response
    ){
        response.setCharacterEncoding("UTF-8");
        try{
            if(bindingResult.hasErrors()){
                redirectAttributes.addFlashAttribute("errors", bindingResult);
                return "redirect:/login/regist";
            }

            boolean result = MemberService.registerMember(memberRegistDTO);
            if(result){
                net.fullstack7.edusecond.edusecond.util.JSFunc.alertLocation("회원가입 성공", "/main/main", response);
                return null;
            }else{
                net.fullstack7.edusecond.edusecond.util.JSFunc.alertBack("회원가입 실패", response);
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //아이디 중복 확인
//    @GetMapping("/regist")
//    public String idCheck(
//            @RequestParam String userId
//            , RedirectAttributes redirectAttributes
//    ){
//        try{
//            MemberDTO result = MemberService.getMember(userId);
//            if(result == null){
//                redirectAttributes.addFlashAttribute("errorMessage", "사용가능한 아이디 입니다.");
//                return "redirect:/main/regist";
//            } else{
//                redirectAttributes.addFlashAttribute("errorMessage", "이미 존재하는 아이디 입니다.");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return "login/regist";
//    }
}