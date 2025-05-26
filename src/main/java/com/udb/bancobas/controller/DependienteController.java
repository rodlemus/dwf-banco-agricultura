package com.udb.bancobas.controller;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.BankAccountRepository;
import com.udb.bancobas.repository.LoanRepository;
import com.udb.bancobas.repository.UserRepository;
import com.udb.bancobas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dependiente")
@RequiredArgsConstructor
public class DependienteController {

    private final UserService userService;

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final LoanRepository loanRepository;

    @GetMapping("/atender")
    public String atenderCliente(Model model) {
        model.addAttribute("userRole", "DEPENDIENTE");
        return "dependiente/servicios-cliente";
    }

    @GetMapping("/buscar-cliente")
    public String mostrarFormularioBusqueda(@RequestParam(value = "dui", required = false) String dui, Model model) {
        System.out.println("Buscando cliente con DUI: " + dui);

        if (dui != null && !dui.isEmpty()) {
            User cliente = userRepository.findByDui(dui);
            if (cliente != null && cliente.getRole() == User.Role.cliente) {
                System.out.println("Cliente encontrado: " + cliente.getName());

                List<BankAccount> cuentas = bankAccountRepository.findByUser(cliente);
                System.out.println("Cuentas encontradas: " + cuentas.size());

                List<Loan> prestamos = loanRepository.findByUser(cliente);
                System.out.println("Préstamos encontrados: " + prestamos.size());

                model.addAttribute("cliente", cliente);
                model.addAttribute("cuentas", cuentas);
                model.addAttribute("prestamos", prestamos);
            } else {
                System.out.println("Cliente no encontrado o no tiene el rol 'cliente'");
                model.addAttribute("error", "Cliente no encontrado o no es un cliente válido.");
            }
        }
        return "dependiente/buscar-cliente";
    }

    @PostMapping("/buscar-cliente")
    public String procesarBusquedaCliente(@RequestParam String dui, Model model) {
        System.out.println("Buscando cliente con DUI (POST): " + dui);

        Optional<User> cliente = userService.getUserByDuiAndRole(dui, User.Role.cliente);
        if (cliente.isPresent()) {
            System.out.println("Cliente encontrado: " + cliente.get().getName());

            List<BankAccount> cuentas = bankAccountRepository.findByUser(cliente.get());
            System.out.println("Cuentas encontradas: " + cuentas.size());

            List<Loan> prestamos = loanRepository.findByUser(cliente.get());
            System.out.println("Préstamos encontrados: " + prestamos.size());

            model.addAttribute("cliente", cliente.get());
            model.addAttribute("cuentas", cuentas);
            model.addAttribute("prestamos", prestamos);
        } else {
            model.addAttribute("error", "Cliente no encontrado.");
        }

        return "dependiente/buscar-cliente";
    }
}
