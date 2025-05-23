package com.udb.bancobas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @GetMapping("/cuentas")
    public String cuentas(Model model) {
        model.addAttribute("pageTitle", "Mis cuentas");
        model.addAttribute("userRole", "CLIENTE");
        // Aquí luego cargarás las cuentas del cliente
        return "cliente/cuentas";
    }

    @GetMapping("/movimientos")
    public String movimientos(Model model) {
        model.addAttribute("pageTitle", "Movimientos");
        model.addAttribute("userRole", "CLIENTE");
        // Aquí cargar movimientos
        return "cliente/movimientos";
    }
}
