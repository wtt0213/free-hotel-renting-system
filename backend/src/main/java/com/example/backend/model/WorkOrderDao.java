package com.example.backend.model;

import java.util.List;

public interface WorkOrderDao {

    WorkOrder saveWorkOrder(WorkOrder workOrder);

    void deleteWorkOrder(String id);

    List<WorkOrder> getAllWorkOrders();

    List<WorkOrder> getAllWorkOrdersByWorkerId(String workerId);

    void completeWorkOrder(String id);

    WorkOrder getWorkOrderById(String id);

    void reviewWorkOrder(String id, double mark, String review);

    WorkOrder getWorkOrderByRepairId(String repairId);

    void markWorkOrder(String id, double mark);
}
