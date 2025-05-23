package com.udb.bancobas.controller;

import com.udb.bancobas.model.Branch;
import com.udb.bancobas.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    // Mostrar todas las sucursales
    @GetMapping
    public String listBranches(Model model) {
        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        return "branches/list"; // Vista: templates/branches/list.html
    }

    // Mostrar detalles de una sucursal
    @GetMapping("/{id}")
    public String viewBranch(@PathVariable Long id, Model model) {
        Branch branch = branchService.getBranchById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        model.addAttribute("branch", branch);
        return "branches/detail"; // Vista: templates/branches/detail.html
    }

    // Mostrar formulario de nueva sucursal
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("branch", new Branch());
        return "branches/form"; // Vista: templates/branches/form.html
    }

    // Guardar nueva sucursal
    @PostMapping
    public String createBranch(@ModelAttribute Branch branch) {
        branchService.createBranch(branch);
        return "redirect:/branches";
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Branch branch = branchService.getBranchById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
        model.addAttribute("branch", branch);
        return "branches/form"; // Reutiliza la misma vista del formulario
    }

    // Actualizar sucursal
    @PostMapping("/update/{id}")
    public String updateBranch(@PathVariable Long id, @ModelAttribute Branch branch) {
        branchService.updateBranch(id, branch);
        return "redirect:/branches";
    }

    // Eliminar sucursal
    @GetMapping("/delete/{id}")
    public String deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return "redirect:/branches";
    }
}
