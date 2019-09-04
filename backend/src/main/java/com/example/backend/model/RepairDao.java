package com.example.backend.model;

import java.util.List;

public interface RepairDao {

    Repair saveRepair(Repair repair);

    void deleteRepair(String id);

    void UpdateWorkOrder(String id, String workOrderId);

    List<Repair> getAllRepairs();

    List<Repair> getAllRepairsByTenantId(String tenantId);

    Repair getRepairById(String id);
}
