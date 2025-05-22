package com.udb.bancobas.repository;

import com.udb.bancobas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    // Buscar usuario por correo electrónico (para login u otras validaciones)
    Optional<User> findByEmail(String email);

    // Buscar usuario por DUI
    Optional<User> findByDui(String dui);

    // Buscar todos los usuarios por rol
    List<User> findByRole(String role);

    // Buscar usuarios por estado
    List<User> findByStatus(String status);

    // Combinar filtros: por rol y estado
    List<User> findByRoleAndStatus(String role, String status);
}
