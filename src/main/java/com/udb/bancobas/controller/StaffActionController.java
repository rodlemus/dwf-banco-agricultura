package com.udb.bancobas.controller;

import com.udb.bancobas.model.StaffAction;
import com.udb.bancobas.model.User;
import com.udb.bancobas.service.StaffActionService;
import com.udb.bancobas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff-actions")
public class StaffActionController {

    @Autowired
    private StaffActionService staffActionService;

    @Autowired
    private UserService userService;

    // Mostrar todas las acciones de personal
    @GetMapping
    public String listStaffActions(Model model) {
        model.addAttribute("actions", staffActionService.getAllStaffActions());
        return "staff-actions/list"; // Vista: templates/staff-actions/list.html
    }

    // Ver detalles de una acción de personal
    @GetMapping("/{id}")
    public String viewStaffAction(@PathVariable Integer id, Model model) {
        staffActionService.getStaffActionById(id).ifPresent(action -> model.addAttribute("action", action));
        return "staff-actions/detail"; // Vista: templates/staff-actions/detail.html
    }

    // Mostrar formulario para nueva acción
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("action", new StaffAction());
        model.addAttribute("users", userService.getAllUsers());
        return "staff-actions/form";
    }

    // Guardar nueva acción
    @PostMapping
    public String saveStaffAction(@ModelAttribute StaffAction staffAction) {
        staffActionService.createStaffAction(staffAction);
        return "redirect:/staff-actions";
    }

    // Eliminar una acción
    @GetMapping("/delete/{id}")
    public String deleteStaffAction(@PathVariable Integer id) {
        staffActionService.deleteStaffAction(id);
        return "redirect:/staff-actions";
    }

    // Filtrar acciones por empleado afectado
    @GetMapping("/employee")
    public String filterByEmployee(@RequestParam("employeeId") Integer employeeId, Model model) {
        User employee = new User();
        employee.setId(employeeId);
        model.addAttribute("actions", staffActionService.getActionsByEmployee(employee));
        return "staff-actions/list";
    }

    // Filtrar por creador
    @GetMapping("/created-by")
    public String filterByCreatedBy(@RequestParam("createdById") Integer createdById, Model model) {
        User createdBy = new User();
        createdBy.setId(createdById);
        model.addAttribute("actions", staffActionService.getActionsByCreatedBy(createdBy));
        return "staff-actions/list";
    }

    // Filtrar por revisor
    @GetMapping("/reviewed-by")
    public String filterByReviewedBy(@RequestParam("reviewedById") Integer reviewedById, Model model) {
        User reviewedBy = new User();
        reviewedBy.setId(reviewedById);
        model.addAttribute("actions", staffActionService.getActionsByReviewedBy(reviewedBy));
        return "staff-actions/list";
    }

    // Filtrar por estado
    @GetMapping("/status")
    public String filterByStatus(@RequestParam("status") String status, Model model) {
        StaffAction.Status actionStatus = StaffAction.Status.valueOf(status.toUpperCase());
        model.addAttribute("actions", staffActionService.getActionsByStatus(actionStatus));
        return "staff-actions/list";
    }
}
