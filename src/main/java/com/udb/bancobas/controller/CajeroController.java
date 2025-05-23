package com.udb.bancobas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cajero")
public class CajeroController {

    @GetMapping("/registrar-cliente")
    public String registrarCliente(Model model) {
        model.addAttribute("userRole", "CAJERO");
        return "cajero/clientes/nuevo";
    }

    @GetMapping("/aperturar-prestamo")
    public String aperturarPrestamo(Model model) {
        model.addAttribute("userRole", "CAJERO");
        return "cajero/prestamos/apertura";
    }
}
