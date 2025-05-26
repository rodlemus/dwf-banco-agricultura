package com.udb.bancobas.controller;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.User;
import com.udb.bancobas.model.Transaction;
import com.udb.bancobas.repository.BankAccountRepository;
import com.udb.bancobas.repository.TransactionRepository;
import com.udb.bancobas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    // Creamos un metodo que guarde un cliente de prueba
    private User clientePrueba() {
        return userRepository.findByDui("66778899-0");
    }

    @GetMapping("/cuentas")
    public String cuentas(Model model) {
        User cliente = clientePrueba();
        model.addAttribute("pageTitle", "Mis cuentas");
        model.addAttribute("userRole", "CLIENTE");

        List<BankAccount> cuentas = bankAccountRepository.findByUser(cliente);
        model.addAttribute("cuentas", cuentas);
        // Aquí luego cargarás las cuentas del cliente
        return "cliente/cuentas";
    }

    @GetMapping("/movimientos")
    public String movimientos(@RequestParam("cuentaId") Long cuentaId, Model model) {
        Optional<BankAccount> cuentaOpt = bankAccountRepository.findById(cuentaId);
        if (cuentaOpt.isPresent()) {
            List<Transaction> movimientos = transactionRepository.findByAccount(cuentaOpt.get());
            model.addAttribute("cuenta", cuentaOpt.get());
            model.addAttribute("movimientos", movimientos);
        } else {
            model.addAttribute("error", "Cuenta no encontrada.");
        }
        return "cliente/movimientos";
    }

}
