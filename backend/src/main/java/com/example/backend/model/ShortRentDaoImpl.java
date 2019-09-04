package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ShortRentDaoImpl implements ShortRentDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveShortRent(ShortRent shortRent) {
        mongoTemplate.save(shortRent);
    }

    @Override
    public void deleteShortRent(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, ShortRent.class);
    }

    @Override
    public void updatePass(String id, Boolean pass) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("pass", pass),
                ShortRent.class);
    }

    @Override
    public void updateReview(String id, String review) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("review", review),
                ShortRent.class);
    }

    @Override
    public void updatePayed(String id, boolean payed) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("payed", payed),
                ShortRent.class);
    }

    @Override
    public void updateEnd(String id, boolean end) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("end", end),
                ShortRent.class);
    }

    @Override
    public ShortRent getShortRentById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is((id))), ShortRent.class);
    }

    @Override
    public List<ShortRent> getShortRentsByTenantId(String tenantId) {
        return mongoTemplate.find(Query.query(Criteria.where("tenant_id").is((tenantId))), ShortRent.class);
    }

    @Override
    public List<ShortRent> getShortRentsByRoomId(String roomId) {
        return mongoTemplate.find(Query.query(Criteria.where("room_id").is(roomId)), ShortRent.class);
    }

    @Override
    public List<ShortRent> getAllShortRents() {
        return mongoTemplate.findAll(ShortRent.class);
    }

    @Override
    public List<ShortRent> getAllPassedShortRents() {
        return mongoTemplate.find(Query.query(Criteria.where("pass").is(new Boolean(true))), ShortRent.class);
    }
}
