package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RepairDaoImpl implements RepairDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Repair saveRepair(Repair repair) {
        return mongoTemplate.save(repair);
    }

    @Override
    public void deleteRepair(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), Repair.class);
    }

    @Override
    public void UpdateWorkOrder(String id, String workOrderId) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(id)), new Update().set("have_work_order", true).set("work_order_id", workOrderId), Repair.class);
    }

    @Override
    public List<Repair> getAllRepairs() {
        return mongoTemplate.findAll(Repair.class);
    }

    @Override
    public List<Repair> getAllRepairsByTenantId(String tenantId) {
        return mongoTemplate.find(Query.query(Criteria.where("tenant_id").is(tenantId)), Repair.class);
    }

    @Override
    public Repair getRepairById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Repair.class);
    }
}
