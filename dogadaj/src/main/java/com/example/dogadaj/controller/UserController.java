package com.example.dogadaj.controller;

import com.example.dogadaj.domain.User;
import com.example.dogadaj.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    final private String title = "DOGadaj | Behawiorysta psów - Karolina Teper |";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("title", title);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model){
        if (result.hasErrors()){
            return "register";
        }

        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title", title);
        return "login";
    }
}
