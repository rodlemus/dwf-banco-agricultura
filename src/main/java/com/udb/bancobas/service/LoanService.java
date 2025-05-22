package com.udb.bancobas.service;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    List<Loan> getAllLoans();

    Optional<Loan> getLoanById(Integer id);

    Loan createLoan(Loan loan);

    Loan updateLoan(Integer id, Loan loan);

    void deleteLoan(Integer id);

    List<Loan> getLoansByUserId(Integer userId);

    List<Loan> getLoansByStatus(Loan.Status status);

    List<Loan> getLoansCreatedBy(User createdBy);

    List<Loan> getLoansApprovedBy(User approvedBy);
}
