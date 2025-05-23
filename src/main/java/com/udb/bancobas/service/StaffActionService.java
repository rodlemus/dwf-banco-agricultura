package com.udb.bancobas.service;

import com.udb.bancobas.model.StaffAction;
import com.udb.bancobas.model.User;

import java.util.List;
import java.util.Optional;

public interface StaffActionService {

    List<StaffAction> getAllStaffActions();

    Optional<StaffAction> getStaffActionById(Integer id);

    StaffAction createStaffAction(StaffAction staffAction);

    void deleteStaffAction(Integer id);

    List<StaffAction> getActionsByEmployee(User employee);

    List<StaffAction> getActionsByCreatedBy(User createdBy);

    List<StaffAction> getActionsByReviewedBy(User reviewedBy);

    List<StaffAction> getActionsByStatus(StaffAction.Status status);
}
