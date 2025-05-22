package com.udb.bancobas.service;

import com.udb.bancobas.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getUsersByRole(String role);
    Optional<User> getUserByEmail(String email);
}
