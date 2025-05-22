package com.udb.bancobas.service;

import com.udb.bancobas.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchService {

    List<Branch> getAllBranches();
    Optional<Branch> getBranchById(Long id);
    Branch createBranch(Branch branch);
    Branch updateBranch(Long id, Branch branch);
    void deleteBranch(Long id);
}
