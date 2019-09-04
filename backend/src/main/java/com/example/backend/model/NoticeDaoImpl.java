package com.example.backend.model;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class NoticeDaoImpl implements NoticeDao{
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveNotice(Notice notice) {
        mongoTemplate.save(notice);
    }

    @Override
    public void deleteNotice(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Notice.class);
    }

    @Override
    public void updateType(String type) {
        Query query = new Query(Criteria.where("type").is(type));
        mongoTemplate.updateFirst(query, new Update().set("type", type), Notice.class);
    }

    @Override
    public void updateContent(String content) {
        Query query = new Query(Criteria.where("content").is(content));
        mongoTemplate.updateFirst(query, new Update().set("content", content), Notice.class);
    }

    @Override
    public List<Notice> getNoticesByTenantId(String tenantId) {
        return mongoTemplate.find(Query.query(Criteria.where("tenant_id").is(tenantId)), Notice.class);
    }

    @Override
    public void setRead(String id){
        mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)), new Update().set("is_read", true), Notice.class);
    }
}
