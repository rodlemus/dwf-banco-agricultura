package com.udb.bancobas.service.impl;

import com.udb.bancobas.model.StaffAction;
import com.udb.bancobas.model.User;
import com.udb.bancobas.repository.StaffActionRepository;
import com.udb.bancobas.service.StaffActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffActionServiceImpl implements StaffActionService {

    @Autowired
    private StaffActionRepository staffActionRepository;

    @Override
    public List<StaffAction> getAllStaffActions() {
        return staffActionRepository.findAll();
    }

    @Override
    public Optional<StaffAction> getStaffActionById(Integer id) {
        return staffActionRepository.findById(id);
    }

    @Override
    public StaffAction createStaffAction(StaffAction staffAction) {
        return staffActionRepository.save(staffAction);
    }

    @Override
    public void deleteStaffAction(Integer id) {
        staffActionRepository.deleteById(id);
    }

    @Override
    public List<StaffAction> getActionsByEmployee(User employee) {
        return staffActionRepository.findByEmployee(employee);
    }

    @Override
    public List<StaffAction> getActionsByCreatedBy(User createdBy) {
        return staffActionRepository.findByCreatedBy(createdBy);
    }

    @Override
    public List<StaffAction> getActionsByReviewedBy(User reviewedBy) {
        return staffActionRepository.findByReviewedBy(reviewedBy);
    }

    @Override
    public List<StaffAction> getActionsByStatus(StaffAction.Status status) {
        return staffActionRepository.findByStatus(status);
    }
}
