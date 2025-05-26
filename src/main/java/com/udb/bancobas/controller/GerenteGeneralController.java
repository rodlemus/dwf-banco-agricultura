package com.udb.bancobas.controller;

import com.udb.bancobas.model.Branch;
import com.udb.bancobas.model.StaffAction;
import com.udb.bancobas.repository.BranchRepository;
import com.udb.bancobas.repository.StaffActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gerente-general")
@RequiredArgsConstructor
public class GerenteGeneralController {

    private final BranchRepository branchRepository;
    private final StaffActionRepository staffActionRepository;

    @GetMapping("/nueva-sucursal")
    public String nuevaSucursal(Model model) {
        model.addAttribute("branch", new Branch());
        model.addAttribute("userRole", "GERENTE_GENERAL");
        return "gerente_general/sucursales/nueva";
    }

    @GetMapping("/nueva-accion-personal")
    public String accionesPersonal(Model model) {
        model.addAttribute("userRole", "GERENTE_GENERAL");
        return "gerente_general/acciones-personal";
    }

    @GetMapping("/sucursales")
    public String listarSucursales(Model model) {
        List<Branch> sucursales = branchRepository.findAll();
        model.addAttribute("sucursales", sucursales);
        return "gerente_general/sucursales/listar";
    }

    @GetMapping("/acciones-personal")
    public String listarAccionesPersonal(Model model) {
        List<StaffAction> acciones = staffActionRepository.findAll();
        model.addAttribute("acciones", acciones);
        model.addAttribute("statusValues", StaffAction.Status.values());
        return "gerente_general/acciones-personal/listar";
    }

    @PostMapping("/guardar-sucursal")
    public String guardarSucursal(@ModelAttribute Branch branch) {
        branchRepository.save(branch);
        return "redirect:/gerente-general/sucursales";
    }

    @GetMapping("/sucursales/editar/{id}")
    public String mostrarFormularioEditarSucursal(@PathVariable Integer id, Model model) {
        Branch sucursal = branchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada: " + id));
        model.addAttribute("sucursal", sucursal);
        return "gerente_general/sucursales/editar";
    }

    @PostMapping("/sucursales/actualizar")
    public String actualizarSucursal(@ModelAttribute Branch sucursal) {
        branchRepository.save(sucursal);
        return "redirect:/gerente-general/sucursales";
    }

    @PostMapping("/acciones-personal/actualizar")
    public String actualizarAccionPersonal(@ModelAttribute("accion") StaffAction accion) {
        StaffAction accionExistente = staffActionRepository.findById(accion.getId())
                .orElseThrow(() -> new IllegalArgumentException("Acción no encontrada con ID: " + accion.getId()));

        accionExistente.setStatus(accion.getStatus());

        staffActionRepository.save(accionExistente);

        return "redirect:/gerente-general/acciones-personal";
    }
}
