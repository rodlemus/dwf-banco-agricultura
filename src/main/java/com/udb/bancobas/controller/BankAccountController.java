package com.udb.bancobas.controller;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.User;
import com.udb.bancobas.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    // Mostrar todas las cuentas
    @GetMapping
    public String listAccounts(Model model) {
        List<BankAccount> accounts = bankAccountService.getAllBankAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts/list"; // Vista: src/main/resources/templates/accounts/list.html
    }

    // Mostrar detalles de una cuenta
    @GetMapping("/{id}")
    public String viewAccount(@PathVariable Long id, Model model) {
        bankAccountService.getBankAccountById(id).ifPresent(account -> model.addAttribute("account", account));
        return "accounts/detail"; // Vista: src/main/resources/templates/accounts/detail.html
    }

    // Mostrar formulario para crear una cuenta
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new BankAccount());
        return "accounts/form"; // Vista: src/main/resources/templates/accounts/form.html
    }

    // Guardar cuenta nueva
    @PostMapping
    public String saveAccount(@ModelAttribute BankAccount account) {
        bankAccountService.createBankAccount(account);
        return "redirect:/accounts";
    }

    // Eliminar cuenta
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return "redirect:/accounts";
    }

    // Filtrar cuentas por usuario
    @GetMapping("/user")
    public String getAccountsByUser(@RequestParam("userId") Integer userId, Model model) {
        User user = new User();
        user.setId(userId);
        List<BankAccount> accounts = bankAccountService.getBankAccountsByUser(user);
        model.addAttribute("accounts", accounts);
        return "accounts/list";
    }
}
