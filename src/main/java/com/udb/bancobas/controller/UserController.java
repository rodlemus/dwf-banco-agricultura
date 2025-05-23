package com.udb.bancobas.controller;

import com.udb.bancobas.model.User;
import com.udb.bancobas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Mostrar todos los usuarios
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list"; // Vista: templates/users/list.html
    }

    // Mostrar detalle de un usuario
    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "users/detail"; // Vista: templates/users/detail.html
    }

    // Mostrar formulario para nuevo usuario
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/form"; // Vista: templates/users/form.html
    }

    // Guardar nuevo usuario
    @PostMapping
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "users/form"; // Reutiliza el mismo formulario
    }

    // Actualizar usuario
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // Eliminar usuario
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // Filtrar por rol
    @GetMapping("/role")
    public String getUsersByRole(@RequestParam("role") String role, Model model) {
        List<User> usersByRole = userService.getUsersByRole(role);
        model.addAttribute("users", usersByRole);
        return "users/list";
    }
}
