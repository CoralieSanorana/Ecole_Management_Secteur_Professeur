package com.ecole.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";  // Va chercher templates/index.html
    }

    @GetMapping("/error")
    public String error() {
        return "error";  // Va chercher templates/error.html
    }
}