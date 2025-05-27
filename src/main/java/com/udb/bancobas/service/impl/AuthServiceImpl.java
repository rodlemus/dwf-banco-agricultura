package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.UserRepository;
import com.udb.bancobas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta");
        }

        return user;
    }

    @Override
    public User buscarPorEmail(String email) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        return optionalUser.orElseThrow(() -> new Exception("Usuario no encontrado"));
    };
}
