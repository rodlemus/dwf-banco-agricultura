package com.udb.bancobas.repository;

import com.udb.bancobas.model.StaffAction;
import com.udb.bancobas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StaffActionRepository extends JpaRepository<StaffAction, Integer> {

    // Obtener acciones de personal por empleado
    List<StaffAction> findByEmployee(User employee);

    // Obtener acciones de personal creadas por un gerente de sucursal
    List<StaffAction> findByCreatedBy(User createdBy);

    // Obtener acciones de personal revisadas por el gerente general
    List<StaffAction> findByReviewedBy(User reviewedBy);

    // Filtrar por estado (en_espera, aceptada, rechazada)
    List<StaffAction> findByStatus(StaffAction.Status status);
}
