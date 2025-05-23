package com.udb.bancobas.controller;

import com.udb.bancobas.model.Transaction;
import com.udb.bancobas.model.User;
import com.udb.bancobas.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Listar todas las transacciones
    @GetMapping
    public String listTransactions(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions/list"; // Vista: src/main/resources/templates/transactions/list.html
    }

    // Mostrar formulario para crear una transacción
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "transactions/form"; // Vista: src/main/resources/templates/transactions/form.html
    }

    // Guardar nueva transacción
    @PostMapping
    public String saveTransaction(@ModelAttribute Transaction transaction) {
        transactionService.createTransaction(transaction);
        return "redirect:/transactions";
    }

    // Ver detalles de una transacción
    @GetMapping("/{id}")
    public String viewTransaction(@PathVariable Integer id, Model model) {
        transactionService.getTransactionById(id).ifPresent(t -> model.addAttribute("transaction", t));
        return "transactions/detail"; // Vista: src/main/resources/templates/transactions/detail.html
    }

    // Eliminar una transacción
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return "redirect:/transactions";
    }

    // Filtrar por tipo
    @GetMapping("/type")
    public String filterByType(@RequestParam("type") Transaction.Type type, Model model) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        model.addAttribute("transactions", transactions);
        return "transactions/list";
    }

    // Filtrar por cuenta
    @GetMapping("/account")
    public String filterByAccount(@RequestParam("accountId") Long accountId, Model model) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        model.addAttribute("transactions", transactions);
        return "transactions/list";
    }

    // Filtrar por usuario
    @GetMapping("/user")
    public String filterByUser(@RequestParam("userId") Integer userId, Model model) {
        User user = new User();
        user.setId(userId);
        List<Transaction> transactions = transactionService.getTransactionsByUser(user);
        model.addAttribute("transactions", transactions);
        return "transactions/list";
    }
}
