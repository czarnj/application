package com.example.application.controller;

import com.example.application.user.UserService;
import com.example.application.user.dto.UserCredentialsDto;
import com.example.application.user.dto.UserRegistrationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;

@Controller
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        UserRegistrationDto user = new UserRegistrationDto(null, null, null, null);
        model.addAttribute("user", user);
        return "registration-form";
    }

    @PostMapping("/register")
    public String register(UserRegistrationDto user) {
        userService.register(user);
        return "redirect:/login?successfull";
    }
}
