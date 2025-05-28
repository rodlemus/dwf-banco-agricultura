package com.udb.bancobas.config;

import com.udb.bancobas.model.User;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GerenteSucursalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return false;
        }

        User user = (User) session.getAttribute("currentUser");
        if (user.getRole() != User.Role.gerente_sucursal) {
            response.sendRedirect(request.getContextPath() + "/unauthorized");
            return false;
        }

        return true;
    }
}
