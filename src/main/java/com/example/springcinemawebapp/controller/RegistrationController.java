package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.UserDTO;
import com.example.springcinemawebapp.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @GetMapping
    public String registerGetForm(@ModelAttribute("user") UserDTO user) {
        return "register";
    }

    @PostMapping
    public String registerSendForm(@ModelAttribute("user") @Valid UserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        registrationService.register(user);
        return "redirect:auth/login?reg";
    }

}
