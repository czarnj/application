package com.example.application.controller;

import com.example.application.exception.PasswordNotMatchException;
import com.example.application.exception.UserNotFoundException;
import com.example.application.user.User;
import com.example.application.user.UserService;
import com.example.application.user.dto.UserDataChangeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {

    private UserService userService;

    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String settingsForm(Model model) {
        try {
            User contextUser = userService.findUserFromContext();
            UserDataChangeDto user = new UserDataChangeDto(contextUser.getFirstName(), contextUser.getLastName(), null, null);
            model.addAttribute("user", user);
            return "settings";
        } catch (UserNotFoundException ex) {
            return "redirect:/login";
        }
    }

    @PostMapping("/settings")
    public String changeData(UserDataChangeDto user) {
        try {
            userService.updateUser(user);
        } catch (PasswordNotMatchException ex) {
            return "redirect:/settings?error";
        } catch (UserNotFoundException ex) {
            return "redirect:/login";
        }
        return "redirect:/home";
    }
}
