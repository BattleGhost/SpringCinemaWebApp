package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/cabinet")
public class CabinetController {

    private final UserService userService;

    @GetMapping
    public String getCabinet(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/";
        }
        User securityUser = ((User) authentication.getPrincipal());
        com.example.springcinemawebapp.model.User user = userService.findByUsername(securityUser.getUsername());
        model.addAttribute("user", user);
        return "cabinet";
    }
}
