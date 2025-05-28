package com.udb.bancobas.controller;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.LoanRepository;
import com.udb.bancobas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<User> clientes = userRepository.findByRole(User.Role.cliente);
        model.addAttribute("clientes", clientes);
        return "cajero/clientes/listar";
    }

    @GetMapping("/prestamos")
    public String listarPrestamos(Model model) {
        List<Loan> prestamos = loanRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        return "cajero/prestamos/listar";
    }

    @GetMapping("/registrar-cliente")
    public String registrarCliente(Model model) {
        model.addAttribute("cliente", new User());
        model.addAttribute("userRole", "CAJERO");
        return "cajero/clientes/nuevo";
    }

    @GetMapping("/aperturar-prestamo")
    public String aperturarPrestamo(Model model) {
        model.addAttribute("prestamo", new Loan());
        List<User> clientes = userRepository.findByRole(User.Role.cliente);
        model.addAttribute("userRole", "CAJERO");
        model.addAttribute("clientes", clientes);
        return "cajero/prestamos/apertura";
    }

    @PostMapping("/guardar-cliente")
    public String guardarCliente(@ModelAttribute("cliente") User cliente) {
        cliente.setRole(User.Role.cliente);
        cliente.setStatus(User.Status.activo);
        // Aquí podrías encriptar la contraseña si usas Spring Security
        userRepository.save(cliente);
        return "redirect:/cajero/clientes";
    }

    @PostMapping("/agregar-solicitud-prestamo")
    public String guardarPrestamo(@ModelAttribute("prestamo") Loan prestamo) {
        // Simulamos que el cajero autenticado es con id = 3, en este caso es el unico cajero que se tiene de momento
        User cajero = userRepository.findById(3).orElse(null);

        if (cajero == null) {
            return "redirect:/error"; // O maneja el error como prefieras
        }

        // Establecer campos obligatorios
        prestamo.setStatus(Loan.Status.en_espera);
        prestamo.setCreatedBy(cajero);
        prestamo.setApprovedBy(null);

        loanRepository.save(prestamo);

        return "redirect:/cajero/prestamos";
    }
}
