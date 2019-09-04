package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "complaint")
public class Complaint {

    @Id
    private String id;

    @Field("tenant_id")
    private String tenantId;

    @Field("room_id")
    private String roomId;

    // 这里要求租客在投诉的时候要拍照上传
    @Field("image")
    private List<String> images;

    // 投诉的具体内容
    @Field("description")
    private String description;

    // 客服针对投诉的回复，如果该值为空，就说明客服还没有处理该投诉
    @Field("review")
    private String review;

    public boolean isIs_reviewed() {
        return is_reviewed;
    }

    public void setIs_reviewed(boolean is_reviewed) {
        this.is_reviewed = is_reviewed;
    }

    @Field("is_reviewed")
    private boolean is_reviewed;

    @Override
    public String toString() {
        return "Complaint{" +
                "id='" + id + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", image='" + images + '\'' +
                ", description='" + description + '\'' +
                ", review='" + review + '\'' +
                '}';
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
