package net.fullstack7.edusecond.edusecond.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/footer")
@RequiredArgsConstructor
@Log4j2
public class FooterController {

    @GetMapping("/intro")
    public String intro(){
        return "footer/intro";
    }

    @GetMapping("/memberterms")
    public String memberterms(){
        return "footer/memberterms";
    }

    @GetMapping("/personalinfo")
    public String personalinfo(){
        return "footer/personalinfo";
    }

}
