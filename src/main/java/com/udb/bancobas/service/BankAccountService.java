package com.udb.bancobas.service;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.User;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    List<BankAccount> getAllBankAccounts();
    Optional<BankAccount> getBankAccountById(Long id);
    List<BankAccount> getBankAccountsByUser(User user);
    BankAccount createBankAccount(BankAccount account);
    void deleteBankAccount(Long id);
}
