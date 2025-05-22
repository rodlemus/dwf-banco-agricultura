package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.Branch;
import com.udb.bancobas.repository.BranchRepository;
import com.udb.bancobas.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(Math.toIntExact(id));
    }

    @Override
    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Long id, Branch updatedBranch) {
        return branchRepository.findById(Math.toIntExact(id))
                .map(branch -> {
                    branch.setName(updatedBranch.getName());
                    branch.setLocation(updatedBranch.getLocation());
                    return branchRepository.save(branch);
                })
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con id " + id));
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepository.deleteById(Math.toIntExact(id));
    }
}
