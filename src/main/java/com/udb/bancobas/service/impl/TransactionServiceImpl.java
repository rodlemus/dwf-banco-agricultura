package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.Transaction;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.TransactionRepository;
import com.udb.bancobas.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Integer id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Override
    public List<Transaction> getTransactionsByUser(User user) {
        return transactionRepository.findByPerformedBy(user);
    }

    @Override
    public List<Transaction> getTransactionsByType(Transaction.Type type) {
        return transactionRepository.findByType(type);
    }
}
