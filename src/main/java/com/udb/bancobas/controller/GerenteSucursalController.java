package com.udb.bancobas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerente-sucursal")
public class GerenteSucursalController {

    @GetMapping("/registrar-empleado")
    public String registrarEmpleado(Model model) {
        model.addAttribute("userRole", "GERENTE_SUCURSAL");
        return "gerente_sucursal/empleados/nuevo";
    }

    @GetMapping("/revisar-prestamos")
    public String revisarPrestamos(Model model) {
        model.addAttribute("userRole", "GERENTE_SUCURSAL");
        return "gerente_sucursal/prestamos/revision";
    }
}
