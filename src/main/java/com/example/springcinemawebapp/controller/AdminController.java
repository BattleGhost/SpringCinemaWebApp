package com.example.springcinemawebapp.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping
    public String panel() {
        return "admin/admin";
    }

}
