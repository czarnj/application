package com.example.application.controller;

import com.example.application.exception.UserNotFoundException;
import com.example.application.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String ADMIN_ROLE = "ADMIN";

    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String allUserPage() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        try {
            boolean isAdmin = userService.hasCurrentUserRole(ADMIN_ROLE);
            model.addAttribute("isAdmin", isAdmin);
        } catch (UserNotFoundException ex) {
            return "redirect:/login";
        }
        return "home";
    }
}
