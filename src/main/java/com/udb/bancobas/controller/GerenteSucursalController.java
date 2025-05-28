package com.udb.bancobas.controller;

import com.udb.bancobas.model.Loan;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.BranchRepository;
import com.udb.bancobas.repository.LoanRepository;
import com.udb.bancobas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gerente-sucursal")
@RequiredArgsConstructor
public class GerenteSucursalController {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final LoanRepository loanRepository;


    @GetMapping("/empleados")
    public String listarEmpleados(Model model) {
        List<User> empleados = userRepository.findByRoleIn(List.of(
                User.Role.dependiente,
                User.Role.cajero
        ));
        model.addAttribute("empleados", empleados);
        return "gerente_sucursal/empleados/listar";
    }

    @GetMapping("/registrar-empleado")
    public String registrarEmpleado(Model model) {
        model.addAttribute("empleado", new User());
        model.addAttribute("roles", List.of(User.Role.cajero, User.Role.dependiente));
        model.addAttribute("sucursales", branchRepository.findAll());
        return "gerente_sucursal/empleados/nuevo";
    }

    @GetMapping("/revisar-prestamos")
    public String listarPrestamos(Model model) {
        List<Loan> prestamos = loanRepository.findAll();
        model.addAttribute("prestamos", prestamos);
        model.addAttribute("statusValues", Loan.Status.values());
        return "gerente_sucursal/prestamos/revision";
    }

    @PostMapping("/registrar-empleado-nuevo")
    public String registrarEmpleado(@ModelAttribute("empleado") User empleado,
                                    RedirectAttributes redirectAttributes) {
        // empleado.setPassword("123456"); // Definimos una contraseña temporal
        userRepository.save(empleado);
        redirectAttributes.addFlashAttribute("exito", "Empleado registrado exitosamente.");
        return "redirect:/gerente-sucursal/empleados";
    }

    // Editar empleados
    @GetMapping("/empleados/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        User empleado = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("empleado", empleado);
        model.addAttribute("roles", List.of(User.Role.cajero, User.Role.dependiente));
        model.addAttribute("statuses", List.of(User.Status.activo, User.Status.inactivo));
        model.addAttribute("sucursales", branchRepository.findAll());
        return "gerente_sucursal/empleados/editar";
    }

    // Procesar edición
    @PostMapping("/empleados/editar/{id}")
    public String actualizarEmpleado(@PathVariable("id") Integer id, @ModelAttribute("empleado") User empleadoActualizado, RedirectAttributes redirectAttributes) {
        User existente = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        existente.setName(empleadoActualizado.getName());
        existente.setEmail(empleadoActualizado.getEmail());
        existente.setDui(empleadoActualizado.getDui());
        existente.setRole(empleadoActualizado.getRole());
        existente.setBranch(empleadoActualizado.getBranch());
        existente.setSalary(empleadoActualizado.getSalary());
        existente.setStatus(empleadoActualizado.getStatus());
        userRepository.save(existente);
        redirectAttributes.addFlashAttribute("exito", "Empleado actualizado correctamente.");
        return "redirect:/gerente-sucursal/empleados";
    }

    // Eliminar empleado
    @PostMapping("/empleados/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("exito", "Empleado eliminado exitosamente.");
        return "redirect:/gerente-sucursal/empleados";
    }

    // PRESTAMOS EDIT
    @PostMapping("/prestamos/actualizar-status")
    public String actualizarStatusPrestamo(@RequestParam Integer id, @RequestParam String status) {
        // Obtener el préstamo por id
        Loan prestamo = loanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado: " + id));

        // Validar que el status recibido es válido
        Loan.Status nuevoStatus;
        try {
            nuevoStatus = Loan.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            // Manejar status inválido, aquí simplemente redirigimos con error (podrías mejorar)
            return "redirect:/gerente-sucursal/prestamos/revision?error=status_invalido";
        }

        // Actualizar el estado
        prestamo.setStatus(nuevoStatus);
        Optional<User> gerente = userRepository.findById(2); // Definimos el id del usurio con rol GERENTE_SUCURSAL DE MOMENTO
        prestamo.setApprovedBy(gerente.get());

        // Guardar el préstamo actualizado
        loanRepository.save(prestamo);

        return "redirect:/gerente-sucursal/revisar-prestamos";
    }
}
