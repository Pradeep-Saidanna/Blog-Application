package com.blog.blogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Spring Boot Blog");
        model.addAttribute("author", "Pradeep");
        return "home";
    }
    @ResponseBody
    @GetMapping("/reached")
    public String reached() {
        return "Blog Application Running Successfully";
    }
    @ResponseBody
    @GetMapping("/about")
    public String about() {
        return "This is my Blog Project";
    }
}

