package com.udb.bancobas.controller.auth;

import com.udb.bancobas.model.User;
import com.udb.bancobas.model.auth.Login;
import com.udb.bancobas.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session,
                                @CookieValue(value = "userEmail", defaultValue = "") String email) {

        if (session.getAttribute("currentUser") != null) {
            return "redirect:/home/dashboard";
        }

        if (!email.isEmpty()) {
            // Si quieres restaurar sesión automáticamente desde cookie
            try {
                User user = authService.buscarPorEmail(email); // debes implementar esto
                session.setAttribute("currentUser", user);
                return "redirect:/home/dashboard";
            } catch (Exception e) {
                // Si falla, simplemente mostrar login
            }
        }

        model.addAttribute("login", new Login());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") Login login,
                        Model model, HttpSession session,
                        HttpServletResponse response) {
        try {
            User user = authService.login(login.getEmail(), login.getPassword());
            session.setAttribute("currentUser", user);

            // Guardar cookie con duración de 7 días
            Cookie cookie = new Cookie("userEmail", user.getEmail());
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 días
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/home/dashboard";

        } catch (Exception e) {
            model.addAttribute("login", login);
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();

        // Eliminar cookie
        Cookie cookie = new Cookie("userEmail", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/auth/login";
    }

}




