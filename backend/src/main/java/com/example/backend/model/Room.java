package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "room_info")
public class Room {

    @Id
    private String id;

    // 房间所在的城市
    @Field("city")
    private String city;

    @Field("location")
    private String location;

    // 这是短期租房的单价，多少钱一天
    @Field("short_price")
    private BigDecimal shortPrice;

    // 这是长期租房的单价，多少钱一个月
    @Field("long_price")
    private BigDecimal longPrice;

    // 房间类型，几人间
    @Field("type")
    private int type;

    // 房间的展示照片，是一个数组，高分辨率部分
    @Field("hdpi_images")
    private List<String> hdpiImages;

    // 房间的展示照片，是一个数组，中分辨率部分
    @Field("mdpi_images")
    private List<String> mdpiImages;

    // 房间的展示照片，是一个数组，低分辨率部分
    @Field("ldpi_images")
    private List<String> ldpiImages;

    @Field("vr_images")
    private List<String> vrImages;

    // 房间当前是不是可用
    @Field("is_available")
    private boolean isAvailable;

    // 房间评分，最高为5分
    @Field("mark")
    private double mark;

    // 房间的文字描述信息
    @Field("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getShortPrice() {
        return shortPrice;
    }

    public void setShortPrice(BigDecimal shortPrice) {
        this.shortPrice = shortPrice;
    }

    public BigDecimal getLongPrice() {
        return longPrice;
    }

    public void setLongPrice(BigDecimal longPrice) {
        this.longPrice = longPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getHdpiImages() {
        return hdpiImages;
    }

    public void setHdpiImages(List<String> hdpiImages) {
        this.hdpiImages = hdpiImages;
    }

    public List<String> getMdpiImages() {
        return mdpiImages;
    }

    public void setMdpiImages(List<String> mdpiImages) {
        this.mdpiImages = mdpiImages;
    }

    public List<String> getLdpiImages() {
        return ldpiImages;
    }

    public void setLdpiImages(List<String> ldpiImages) {
        this.ldpiImages = ldpiImages;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getVrImages() {
        return vrImages;
    }

    public void setVrImages(List<String> vrImages) {
        this.vrImages = vrImages;
    }

    @Override
    public String toString() {
        return "Room{" + "id='" + id + '\'' + ", city='" + city + '\'' + ", location='" + location + '\''
                + ", shortPrice=" + shortPrice + ", longPrice=" + longPrice + ", type=" + type + ", hdpiImages="
                + hdpiImages + ", mdpiImages=" + mdpiImages + ", ldpiImages=" + ldpiImages + ", vrImages=" + vrImages
                + ", isAvailable=" + isAvailable + ", mark=" + mark + ", description='" + description + '\'' + '}';
    }
}