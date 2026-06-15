package com.blog.blogapp.controller;

import com.blog.blogapp.dto.RegisterDto;
import com.blog.blogapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new RegisterDto());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") RegisterDto registerDto) {

        userService.saveUser(registerDto);

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {

        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password!");
        }

        if (logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out successfully!");
        }

        return "login";
    }
}