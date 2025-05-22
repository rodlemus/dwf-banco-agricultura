package com.udb.bancobas.repository;

import com.udb.bancobas.model.BankAccount;
import com.udb.bancobas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    // Obtener todas las cuentas de un usuario por su ID
    List<BankAccount> findByUserId(User user);

    // Verificar existencia de una cuenta por ID
    Optional<BankAccount> findById(Long id);
}
