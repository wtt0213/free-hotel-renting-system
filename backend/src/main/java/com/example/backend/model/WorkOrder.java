package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class WorkOrder {

    @Id
    private String id;

    @Field("repair_id")
    private String repairId;

    @Field("worker_id")
    private String workerId;

    @Field("is_complete")
    private boolean is_complete;

    @Field("mark")
    private double mark;

    @Field("review")
    private String review;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public boolean isIs_complete() {
        return is_complete;
    }

    public void setIs_complete(boolean is_complete) {
        this.is_complete = is_complete;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id='" + id + '\'' +
                ", repairId='" + repairId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", is_complete='" + is_complete + '\'' +
                ", mark=" + mark +
                ", review='" + review + '\'' +
                '}';
    }
}
