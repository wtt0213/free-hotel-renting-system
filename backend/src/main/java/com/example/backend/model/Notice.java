package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Notice {

    @Id
    private String id;

    // 表示该通知是发给谁的
    @Field("tenant_id")
    private String tenantId;

    // 表示该通知是通知啥的，跟哪个订单类、投诉类、报修类相关联
    @Field("object_id")
    private String objectId;

    // 表示该通知是哪一类通知
    @Field("type")
    private String type;

    // 通知的具体内容
    @Field("content")
    private String content;

    // 通知是否已经被读取
    @Field("is_read")
    private Boolean isRead;

    @Override
    public String toString() {
        return "Notice{" +
                "id='" + id + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", objectId='" + objectId + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
