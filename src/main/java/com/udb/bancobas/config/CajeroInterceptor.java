package com.udb.bancobas.config;

import com.udb.bancobas.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CajeroInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return false;
        }

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser.getRole() != User.Role.cajero) {
            response.sendRedirect(request.getContextPath() + "/auth/login?unauthorized");
            return false;
        }

        return true;
    }
}
