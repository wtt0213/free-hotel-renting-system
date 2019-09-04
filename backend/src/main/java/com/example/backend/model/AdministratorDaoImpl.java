package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AdministratorDaoImpl implements AdministratorDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Administrator getAdministratorByName(String name) {
        return mongoTemplate.findOne(Query.query(Criteria.where("name").is(name)), Administrator.class);
    }

    @Override
    public Administrator getAdministratorById(String id) {
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Administrator.class);
    }

    @Override
    public List<Administrator> getAllAdministrator() {
        return mongoTemplate.findAll(Administrator.class);

    }


}
