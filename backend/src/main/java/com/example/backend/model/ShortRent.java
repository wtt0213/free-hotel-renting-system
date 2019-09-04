package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "short_rent_info")
public class ShortRent {

    @Id
    private String id;

    @Field("tenant_id")
    private String tenantId;

    @Field("room_id")
    private String roomId;

    // 该租房记录生成的时间
    @Field("add_time")
    private Date addDate = new Date();

    // 该租房记录开始的时间
    @Field("start_time")
    private Date startDate;

    // 该租房记录结束的时间
    @Field("end_time")
    private Date endDate;

    // 该租房记录的总金额
    @Field("price")
    private BigDecimal price;

    // 该租房申请是否被客服批准，注意这是对象，如果该对象为空，表示客服还没有处理该申请
    @Field("pass")
    private Boolean pass;

    // 客服对租房申请作出的评论
    @Field("review")
    private String review;

    // 该租房记录是否已付款
    @Field("payed")
    private boolean payed;

    // 该租房记录的生命周期是否已经终结
    // 租房时间到期，租客提前离开，租客在租房记录开始之前没有付款，等等，都会导致租房记录生命周期的终结
    @Field("end")
    private boolean end;

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

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "ShortRent{" +
                "id='" + id + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", addDate=" + addDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", pass=" + pass +
                ", review='" + review + '\'' +
                ", paied=" + payed +
                ", end=" + end +
                '}';
    }
}
