package com.udb.bancobas.repository;

import com.udb.bancobas.model.Transaction;
import com.udb.bancobas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Obtener todas las transacciones de una cuenta específica
    List<Transaction> findByAccountId(Long accountId);

    // Obtener todas las transacciones realizadas por un usuario específico
    List<Transaction> findByPerformedBy(User user);

    // Filtrar por tipo de transacción (deposito, retiro, transferencia)
    List<Transaction> findByType(Transaction.Type type);
}
