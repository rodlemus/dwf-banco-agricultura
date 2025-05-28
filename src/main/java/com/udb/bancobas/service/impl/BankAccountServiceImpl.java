package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.BankAccountRepository;
import com.udb.bancobas.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public List<BankAccount> getBankAccountsByUser(User user) {
        return bankAccountRepository.findByUser(user);
    }

    @Override
    public BankAccount createBankAccount(BankAccount account) {
        return bankAccountRepository.save(account);
    }

    @Override
    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(Math.toIntExact(id));
    }
}
