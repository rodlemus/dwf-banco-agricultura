package com.udb.bancobas.controller;

import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final UserRepository userRepository;

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

    @PostMapping("/guardar-cliente")
    public String guardarCliente(@ModelAttribute("cliente") User cliente) {
        cliente.setRole(User.Role.cliente);
        cliente.setStatus(User.Status.activo);
        // Aquí podrías encriptar la contraseña si usas Spring Security
        userRepository.save(cliente);
        return "redirect:/cajero/clientes";
    }
}
