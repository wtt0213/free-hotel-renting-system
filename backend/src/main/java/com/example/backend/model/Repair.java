package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("repair")
public class Repair {

    @Id
    private String id;

    @Field("tenant_id")
    private String tenantId;

    // 报修的对象可能是长租房，也可能是短租记录
    @Field("long_rent_id")
    private String longRentId;

    @Field("short_rent_id")
    private String shortRentId;

    // 该报修目前有没有生成工单，也就意味着有没有被客服审核
    @Field("have_work_order")
    private boolean haveWordOrder;

    @Field("images")
    private List<String> images;

    @Field("description")
    private String description;

    @Field("work_order_id")
    private String workOrderId;

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

    public String getLongRentId() {
        return longRentId;
    }

    public void setLongRentId(String longRentId) {
        this.longRentId = longRentId;
    }

    public String getShortRentId() {
        return shortRentId;
    }

    public void setShortRentId(String shortRentId) {
        this.shortRentId = shortRentId;
    }

    public boolean isHaveWordOrder() {
        return haveWordOrder;
    }

    public void setHaveWordOrder(boolean haveWordOrder) {
        this.haveWordOrder = haveWordOrder;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id='" + id + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", longRentId='" + longRentId + '\'' +
                ", shortRentId='" + shortRentId + '\'' +
                ", haveWordOrder=" + haveWordOrder +
                ", images=" + images +
                ", description='" + description + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                '}';
    }
}
