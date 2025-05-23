package com.udb.bancobas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerente-general")
public class GerenteGeneralController {

    @GetMapping("/nueva-sucursal")
    public String nuevaSucursal(Model model) {
        model.addAttribute("userRole", "GERENTE_GENERAL");
        return "gerente_general/sucursales/nueva";
    }

    @GetMapping("/acciones-personal")
    public String accionesPersonal(Model model) {
        model.addAttribute("userRole", "GERENTE_GENERAL");
        return "gerente_general/acciones-personal";
    }
}
