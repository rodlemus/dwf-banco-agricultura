package com.udb.bancobas.controller;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import com.udb.bancobas.service.LoanService;
import com.udb.bancobas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    // Mostrar todos los préstamos
    @GetMapping
    public String listLoans(Model model) {
        model.addAttribute("loans", loanService.getAllLoans());
        return "loans/list"; // Vista: templates/loans/list.html
    }

    // Mostrar detalles de un préstamo
    @GetMapping("/{id}")
    public String viewLoan(@PathVariable Integer id, Model model) {
        loanService.getLoanById(id).ifPresent(loan -> model.addAttribute("loan", loan));
        return "loans/detail"; // Vista: templates/loans/detail.html
    }

    // Mostrar formulario para crear préstamo
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("loan", new Loan());
        model.addAttribute("users", userService.getAllUsers());
        return "loans/form"; // Vista: templates/loans/form.html
    }

    // Guardar préstamo
    @PostMapping
    public String saveLoan(@ModelAttribute Loan loan) {
        loanService.createLoan(loan);
        return "redirect:/loans";
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Loan loan = loanService.getLoanById(id).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
        model.addAttribute("loan", loan);
        model.addAttribute("users", userService.getAllUsers());
        return "loans/form";
    }

    // Actualizar préstamo
    @PostMapping("/update/{id}")
    public String updateLoan(@PathVariable Integer id, @ModelAttribute Loan loan) {
        loanService.updateLoan(id, loan);
        return "redirect:/loans";
    }

    // Eliminar préstamo
    @GetMapping("/delete/{id}")
    public String deleteLoan(@PathVariable Integer id) {
        loanService.deleteLoan(id);
        return "redirect:/loans";
    }

    // Filtrar por usuario
    @GetMapping("/user")
    public String getLoansByUser(@RequestParam("userId") Integer userId, Model model) {
        model.addAttribute("loans", loanService.getLoansByUserId(userId));
        return "loans/list";
    }

    // Filtrar por estado
    @GetMapping("/status")
    public String getLoansByStatus(@RequestParam("status") Loan.Status status, Model model) {
        model.addAttribute("loans", loanService.getLoansByStatus(status));
        return "loans/list";
    }

    // Filtrar por creador
    @GetMapping("/created-by")
    public String getLoansCreatedBy(@RequestParam("userId") Integer userId, Model model) {
        User user = userService.getUserById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("loans", loanService.getLoansCreatedBy(user));
        return "loans/list";
    }

    // Filtrar por aprobador
    @GetMapping("/approved-by")
    public String getLoansApprovedBy(@RequestParam("userId") Integer userId, Model model) {
        User user = userService.getUserById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("loans", loanService.getLoansApprovedBy(user));
        return "loans/list";
    }
}
