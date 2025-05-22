package com.udb.bancobas.repository;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  LoanRepository extends JpaRepository<Loan, Integer> {

    // Obtener préstamos por usuario (cliente)
    List<Loan> findByUserId(Integer userId);

    // Obtener préstamos por estado (en_espera, aprobado, rechazado)
    List<Loan> findByStatus(Loan.Status status);

    // Buscar préstamos creados por un usuario específico (por objeto User)
    List<Loan> findByCreatedBy(User createdBy);

    // Buscar préstamos aprobados por un usuario específico (por objeto User)
    List<Loan> findByApprovedBy(User approvedBy);
}
