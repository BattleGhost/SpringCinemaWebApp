package com.example.springcinemawebapp.controller;

import com.example.springcinemawebapp.dto.UserDTO;
import com.example.springcinemawebapp.exception.EmailIsAlreadyTakenException;
import com.example.springcinemawebapp.service.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Log4j2
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
        log.info(user);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            registrationService.register(user);
        } catch (EmailIsAlreadyTakenException e) {
            bindingResult.addError(new ObjectError("emailIsAlreadyTaken",
                    e.getLocalizedMessage()));
            return "register";
        }

        return "redirect:auth/login?reg";
    }

}
