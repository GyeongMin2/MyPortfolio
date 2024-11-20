package net.fullstack7.edusecond.edusecond.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
  @GetMapping("/")
  public String root() {
    return "redirect:/main/main";
  }
}
