package com.udb.bancobas.controller;

import com.udb.bancobas.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home/dashboard")
    public String home(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pageTitle", "Inicio");
        model.addAttribute("userRole", currentUser.getRole().name()); // Usamos el rol real
        model.addAttribute("userName", currentUser.getName()); // si quieres mostrar nombre

        return "home/dashboard";
    }
}
