package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class LongRentDaoImpl implements LongRentDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveLongRent(LongRent longRent) {
        mongoTemplate.save(longRent);
    }

    @Override
    public void deleteLongRent(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, LongRent.class);
    }

    @Override
    public void updatePass(String id, Boolean pass) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("pass", pass),
                LongRent.class);
    }

    @Override
    public void updateReview(String id, String review) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("review", review),
                LongRent.class);

    }

    /*
     * 修改数组
     */
    @Override
    public void updatePayed(String id, boolean payed, int num) {
        Query query = new Query(Criteria.where("_id").is((id)));
        LongRent longRent = mongoTemplate.findOne(query, LongRent.class);
        ArrayList<Boolean> payedList = longRent.getPayed();
        payedList.set(num - 1, payed);
        longRent.setPayed(payedList);
        mongoTemplate.updateFirst(query, new Update().set("payed", longRent.getPayed()), LongRent.class);
    }

    @Override
    public void renew(String id, int num) {
        Query query = new Query(Criteria.where("_id").is((id)));
        LongRent longRent = mongoTemplate.findOne(query, LongRent.class);

        // 更新payed数组，末尾添加num个false
        ArrayList<Boolean> payedList = longRent.getPayed();
        for (int i = 0; i < num; i++)
            payedList.add(Boolean.FALSE);
        longRent.setPayed(payedList);

        // 更新endDate
        Date endDate = longRent.getEndDate();
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.add(Calendar.MONTH, num);
        endDate = endCal.getTime();

        // 更新end，租房记录未终结
        longRent.setEnd(false);

        mongoTemplate.updateFirst(query,
                new Update().set("payed", longRent.getPayed()).set("end", longRent.isEnd()).set("end_time", endDate),
                LongRent.class);

    }

    @Override
    public boolean getIsRenew(String id) {
        Query query = new Query(Criteria.where("_id").is((id)));
        LongRent longRent = mongoTemplate.findOne(query, LongRent.class);
        return longRent.isRenew();
    }

    @Override
    public void updateIsRenew(String id, boolean isRenew) {
        Query query = new Query(Criteria.where("_id").is((id)));
        LongRent longRent = mongoTemplate.findOne(query, LongRent.class);
        longRent.setRenew(isRenew);
        mongoTemplate.updateFirst(query, new Update().set("renew", longRent.isRenew()), LongRent.class);
    }

    @Override
    public void updateEnd(String id, boolean end) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is((id))), new Update().set("end", end),
                LongRent.class);
    }

    @Override
    public LongRent getLongRentById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is((id))), LongRent.class);
    }

    @Override
    public List<LongRent> getLongRentsByTenantId(String tenantId) {
        return mongoTemplate.find(Query.query(Criteria.where("tenant_id").is((tenantId))), LongRent.class);
    }

    @Override
    public List<LongRent> getLongRentsByRoomId(String roomId) {
        return mongoTemplate.find(Query.query(Criteria.where("room_id").is(roomId)), LongRent.class);
    }

    @Override
    public List<LongRent> getAllLongRents() {
        return mongoTemplate.findAll(LongRent.class);
    }

    @Override
    public List<LongRent> getAllPassedLongRents() {
        return mongoTemplate.find(Query.query(Criteria.where("pass").is(new Boolean(true))), LongRent.class);
    }

    @Override
    public void updateContract(String id, boolean contract) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(id)), new Update().set("contract", contract),
                LongRent.class);
    }

}
