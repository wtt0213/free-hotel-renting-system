package com.example.backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "worker_info")
public class Worker {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field
    private String password;

    @Field("phone")
    private String phone;

    @Field("image")
    private String image;

    @Field("mark")
    private List<Double> mark;

    @Field("a_mark")
    private double avalMark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the mark
     */
    public List<Double> getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(List<Double> mark) {
        this.mark = mark;
        Double allMarks = 0.0;
        for (Double eachMark : mark) {
            allMarks += eachMark;
        }
        avalMark = allMarks / mark.size();
    }

    /**
     * @return the avalMark
     */
    public double getAvalMark() {
        return avalMark;
    }

    /**
     * @param avalMark the avalMark to set
     */
    public void setAvalMark(double avalMark) {
        this.avalMark = avalMark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Worker{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", password='" + password + '\''
                + ", phone='" + phone + '\'' + ", image='" + image + '\'' + ", mark=" + mark + '}';
    }
}
