package com.udb.bancobas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dependiente")
public class DependienteController {

    @GetMapping("/atender")
    public String atenderCliente(Model model) {
        model.addAttribute("userRole", "DEPENDIENTE");
        return "dependiente/servicios-cliente";
    }
}
