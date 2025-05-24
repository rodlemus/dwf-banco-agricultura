package com.udb.bancobas.controller;

import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<User> clientes = userRepository.findByRole(User.Role.cliente);
        model.addAttribute("clientes", clientes);
        return "cajero/clientes/listar";
    }

    @GetMapping("/registrar-cliente")
    public String registrarCliente(Model model) {
        model.addAttribute("cliente", new User());
        model.addAttribute("userRole", "CAJERO");
        return "cajero/clientes/nuevo";
    }

    @GetMapping("/aperturar-prestamo")
    public String aperturarPrestamo(Model model) {
        model.addAttribute("userRole", "CAJERO");
        return "cajero/prestamos/apertura";
    }
}
