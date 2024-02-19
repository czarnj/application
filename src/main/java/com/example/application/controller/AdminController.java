package com.example.application.controller;

import com.example.application.exception.UserNotFoundException;
import com.example.application.user.User;
import com.example.application.user.UserService;
import com.example.application.user.dto.UserRoleChangeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String adminPanel(Model model) {
        try {
            List<User> users = userService.findAllUsersWithoutCurrent();
            model.addAttribute("users", users);
        } catch (UserNotFoundException ex) {
            return "redirect:/login";
        }
        return "admin";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam long id, Model model) {
        model.addAttribute("user", new UserRoleChangeDto(id, new HashSet<>()));
        model.addAttribute("listRoles", userService.findAllRoles());
        return "edit-form";
    }

    @PostMapping("/edit")
    public String edit(UserRoleChangeDto user) {
        userService.updateUserRoles(user);
        return "redirect:/admin";
    }
}
