package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.UserRepository;
import com.udb.bancobas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(Math.toIntExact(id));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(Math.toIntExact(id))
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setDui(updatedUser.getDui());
                    user.setRole(updatedUser.getRole());
                    user.setSalary(updatedUser.getSalary());
                    user.setBranch(updatedUser.getBranch());
                    user.setStatus(updatedUser.getStatus());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
