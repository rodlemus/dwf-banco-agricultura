package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.LoanRepository;
import com.udb.bancobas.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Optional<Loan> getLoanById(Integer id) {
        return loanRepository.findById(id);
    }

    @Override
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan updateLoan(Integer id, Loan updatedLoan) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setAmountRequested(updatedLoan.getAmountRequested());
                    loan.setInterestRate(updatedLoan.getInterestRate());
                    loan.setMonthlyPayment(updatedLoan.getMonthlyPayment());
                    loan.setYearsToPay(updatedLoan.getYearsToPay());
                    loan.setStatus(updatedLoan.getStatus());
                    loan.setApprovedBy(updatedLoan.getApprovedBy());
                    return loanRepository.save(loan);
                })
                .orElse(null);
    }

    @Override
    public void deleteLoan(Integer id) {
        loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> getLoansByUserId(Integer userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public List<Loan> getLoansByStatus(Loan.Status status) {
        return loanRepository.findByStatus(status);
    }

    @Override
    public List<Loan> getLoansCreatedBy(User createdBy) {
        return loanRepository.findByCreatedBy(createdBy);
    }

    @Override
    public List<Loan> getLoansApprovedBy(User approvedBy) {
        return loanRepository.findByApprovedBy(approvedBy);
    }
}
