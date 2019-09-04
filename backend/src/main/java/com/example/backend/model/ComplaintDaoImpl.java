package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ComplaintDaoImpl implements ComplaintDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveComplaint(Complaint complaint) {
        mongoTemplate.save(complaint);
    }

    @Override
    public void deleteComplaint(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Complaint.class);
    }

    @Override
    public void updateReview(String id, String review) {
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), new Update().set("review", review).set("is_reviewed", true), Complaint.class);

    }

    public Complaint getComplaintById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Complaint.class);
    }

    @Override
    public List<Complaint> getComplaintByTenantId(String tenantId) {
        return mongoTemplate.find(Query.query(Criteria.where("tenant_id").is(tenantId)),Complaint.class);
    }

    @Override
    public List<Complaint> getComplaintByRoomId(String roomId) {
        return mongoTemplate.find(Query.query(Criteria.where("room_id").is(roomId)),Complaint.class);
    }

    @Override
    public List<Complaint> getAllComplaint() {
        return mongoTemplate.findAll(Complaint.class);
    }
}
