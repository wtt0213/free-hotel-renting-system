package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "long_rent_info")
public class LongRent {

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

    // 该租房申请是否被客服批准，注意这是对象，如果该对象为空，表示客服还没有处理该申请
    @Field("pass")
    private Boolean pass;

    // 客服对租房申请作出的评论
    @Field("review")
    private String review;

    // 该租房记录的付款情况
    @Field("payed")
    private ArrayList<Boolean> payed;

    // 该租房记录的生命周期是否已经终结
    // 租房时间到期，租客提前离开，租客在逾期之后没有付款，等等，都会导致租房记录生命周期的终结
    @Field("end")
    private boolean end;

    /**
     * 表示租客有没有和公司签署租房合同，是个布尔值
     */
    @Field("contract")
    private boolean contract;

    /**
     * 表示租客有没有续租过，是个布尔值
     */
    @Field("renew")
    private boolean isRenew;

    public boolean isRenew() {
        return isRenew;
    }

    public void setRenew(boolean renew) {
        isRenew = renew;
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return String return the roomId
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * @return Date return the addDate
     */
    public Date getAddDate() {
        return addDate;
    }

    /**
     * @param addDate the addDate to set
     */
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    /**
     * @return Date return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return Date return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return Boolean return the pass
     */
    public Boolean getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    /**
     * @return String return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * @return ArrayList<Boolean> return the payed
     */
    public ArrayList<Boolean> getPayed() {
        return payed;
    }

    /**
     * @param payed the payed to set
     */
    public void setPayed(ArrayList<Boolean> payed) {
        this.payed = payed;
    }

    /**
     * @return boolean return the end
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * @return boolean return the contract
     */
    public boolean isContract() {
        return contract;
    }

    /**
     * @param contract the contract to set
     */
    public void setContract(boolean contract) {
        this.contract = contract;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", roomId='" + getRoomId() + "'" +
            ", addDate='" + getAddDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", pass='" + getPass() + "'" +
            ", review='" + getReview() + "'" +
            ", payed='" + getPayed() + "'" +
            ", end='" + isEnd() + "'" +
            ", contract='" + isContract() + "'" +
            "}";
    }


}
