package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class WorkOrderDaoImpl implements WorkOrderDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public WorkOrder saveWorkOrder(WorkOrder workOrder) {
        return mongoTemplate.save(workOrder);
    }

    @Override
    public void deleteWorkOrder(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)));
    }

    @Override
    public List<WorkOrder> getAllWorkOrders() {
        return mongoTemplate.findAll(WorkOrder.class);
    }

    @Override
    public List<WorkOrder> getAllWorkOrdersByWorkerId(String workerId) {
        return mongoTemplate.find(Query.query(Criteria.where("worker_id").is(workerId)), WorkOrder.class);
    }

    @Override
    public void completeWorkOrder(String id) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().set("is_complete", true),
                WorkOrder.class);
    }

    @Override
    public WorkOrder getWorkOrderById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), WorkOrder.class);
    }

    @Override
    public void reviewWorkOrder(String id, double mark, String review) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)),
                new Update().set("mark", mark).set("review", review), WorkOrder.class);
    }

    @Override
    public WorkOrder getWorkOrderByRepairId(String repairId) {
        return mongoTemplate.findOne(Query.query(Criteria.where("repair_id").is(repairId)), WorkOrder.class);
    }

    @Override
    public void markWorkOrder(String id, double mark) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().set("mark", mark),
                WorkOrder.class);
    }
}
