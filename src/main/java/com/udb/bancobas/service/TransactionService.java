package com.udb.bancobas.service;

import com.udb.bancobas.model.Transaction;
import com.udb.bancobas.model.User;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    Optional<Transaction> getTransactionById(Integer id);
    Transaction createTransaction(Transaction transaction);
    void deleteTransaction(Integer id);

    List<Transaction> getTransactionsByAccountId(Long accountId);
    List<Transaction> getTransactionsByUser(User user);
    List<Transaction> getTransactionsByType(Transaction.Type type);
}
