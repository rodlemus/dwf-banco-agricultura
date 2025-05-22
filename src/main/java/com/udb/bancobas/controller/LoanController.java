package com.udb.bancobas.controller;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import com.udb.bancobas.service.LoanService;
import com.udb.bancobas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")

public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserService userService;

    // Obtener todos los préstamos
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    // Obtener un préstamo por ID
    @GetMapping("/{id}")
    public Optional<Loan> getLoanById(@PathVariable Integer id) {
        return loanService.getLoanById(id);
    }

    // Crear un nuevo préstamo
    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    // Actualizar un préstamo
    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable Integer id, @RequestBody Loan loan) {
        return loanService.updateLoan(id, loan);
    }

    // Eliminar un préstamo
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Integer id) {
        loanService.deleteLoan(id);
    }

    // Obtener préstamos por ID de usuario (cliente)
    @GetMapping("/user/{userId}")
    public List<Loan> getLoansByUser(@PathVariable Integer userId) {
        return loanService.getLoansByUserId(userId);
    }

    // Obtener préstamos por estado
    @GetMapping("/status/{status}")
    public List<Loan> getLoansByStatus(@PathVariable Loan.Status status) {
        return loanService.getLoansByStatus(status);
    }

    // Obtener préstamos creados por un usuario específico (objeto User)
    @GetMapping("/created-by/{userId}")
    public List<Loan> getLoansCreatedBy(@PathVariable Integer userId) {
        User user = userService.getUserById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return loanService.getLoansCreatedBy(user);
    }

    // Obtener préstamos aprobados por un usuario específico (objeto User)
    @GetMapping("/approved-by/{userId}")
    public List<Loan> getLoansApprovedBy(@PathVariable Integer userId) {
        User user = userService.getUserById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return loanService.getLoansApprovedBy(user);
    }
}
