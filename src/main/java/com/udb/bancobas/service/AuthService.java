package com.udb.bancobas.service;

import com.udb.bancobas.model.User;

public interface AuthService {

    User login(String email, String password) throws Exception;

    User buscarPorEmail(String email) throws Exception;
}
