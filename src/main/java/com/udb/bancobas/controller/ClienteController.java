package com.udb.bancobas.controller;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.User;
import com.udb.bancobas.model.Transaction;
import com.udb.bancobas.repository.BankAccountRepository;
import com.udb.bancobas.repository.TransactionRepository;
import jakarta.servlet.http.HttpSession;
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

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/cuentas")
    public String cuentas(Model model, HttpSession session) {
        User cliente = (User) session.getAttribute("currentUser");

        if (cliente == null || cliente.getRole() != User.Role.cliente) {
            return "redirect:/auth/login";
        }

        model.addAttribute("pageTitle", "Mis cuentas");
        model.addAttribute("userRole", "cliente");
        model.addAttribute("userName", cliente.getName());

        List<BankAccount> cuentas = bankAccountRepository.findByUser(cliente);
        model.addAttribute("cuentas", cuentas);

        return "cliente/cuentas";
    }

    @GetMapping("/movimientos")
    public String movimientos(@RequestParam("cuentaId") Long cuentaId,
                              Model model, HttpSession session) {
        User cliente = (User) session.getAttribute("currentUser");

        if (cliente == null || cliente.getRole() != User.Role.cliente) {
            return "redirect:/auth/login";
        }

        Optional<BankAccount> cuentaOpt = bankAccountRepository.findById(cuentaId);

        if (cuentaOpt.isPresent()) {
            BankAccount cuenta = cuentaOpt.get();

            // Validar que la cuenta pertenezca al cliente autenticado
            if (!cuenta.getUser().getId().equals(cliente.getId())) {
                model.addAttribute("error", "No tienes permiso para ver esta cuenta.");
                return "cliente/movimientos";
            }

            List<Transaction> movimientos = transactionRepository.findByAccount(cuenta);
            model.addAttribute("cuenta", cuenta);
            model.addAttribute("movimientos", movimientos);

        } else {
            model.addAttribute("error", "Cuenta no encontrada.");
        }

        model.addAttribute("userRole", "cliente");
        model.addAttribute("userName", cliente.getName());

        return "cliente/movimientos";
    }

}
