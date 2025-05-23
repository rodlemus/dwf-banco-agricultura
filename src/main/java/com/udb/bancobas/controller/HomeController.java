package com.udb.bancobas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home/dashboard")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Inicio");
        model.addAttribute("userRole", "CAJERO"); // Por ahora, hardcodeado
        return "home/dashboard"; // Esto busca home/dashboard.html en /templates
    }
}
