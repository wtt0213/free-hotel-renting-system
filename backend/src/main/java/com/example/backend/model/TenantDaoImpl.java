package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TenantDaoImpl implements TenantDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveTenant(Tenant tenant) {
        mongoTemplate.save(tenant);
    }

    @Override
    public boolean isValidName(String newName) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(newName)),Tenant.class)==null;
    }

    @Override
    public boolean isValidEmail(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)),Tenant.class)==null;
    }

    @Override
    public String getPasswordByEmail(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)),Tenant.class).getPassword();
    }

    @Override
    public String getPasswordByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)),Tenant.class).getPassword();
    }

    @Override
    public Tenant getTenantByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)),Tenant.class);
    }

    @Override
    public void activeTenant(String id) {
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), new Update().set("is_active", true), Tenant.class);
}

    @Override
    public Tenant getTenantById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),Tenant.class);
    }

    @Override
    public Tenant getTenantByEmail(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)),Tenant.class);
    }

    @Override
    public void deleteTenant(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), Tenant.class);
    }

    @Override
    public void updatePassword(String id, String password) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().set("password", password);
        mongoTemplate.updateFirst(query, update, Tenant.class);
    }

    @Override
    public void updateName(String id, String name) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().set("name", name);
        mongoTemplate.updateFirst(query, update, Tenant.class);
    }

    @Override
    public void updateEmail(String id, String email) {
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update().set("email", email);
        mongoTemplate.updateFirst(query, update, Tenant.class);
    }

    @Override
    public List<Tenant> getAllTenant(){
        return mongoTemplate.findAll(Tenant.class);
    }

    @Override
    public List<Tenant> getAllTenantsBlurry(String name) {
        Pattern pattern = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
        return mongoTemplate.find(Query.query(Criteria.where("name").regex(pattern)), Tenant.class);
    }
}
