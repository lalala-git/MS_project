package com.yyds.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserBaseInfo {

    private Long userId;

    private String email;

    private String name;

    private String password;

    private String thumb;

    private Integer position;

    private Integer age;

    private String sex;

    private String bir;

    private String constellation;

    private String phone;

    public UserBaseInfo() {
    }

    public UserBaseInfo(Long userId, String email, String password, String thumb, Integer position, Integer age, String sex, String bir, String constellation, String phone) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.thumb = thumb;
        this.position = position;
        this.age = age;
        this.sex = sex;
        this.bir = bir;
        this.constellation = constellation;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBir() {
        return bir;
    }

    public void setBir(String bir) {
        this.bir = bir;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserBaseInfo{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", thumb='" + thumb + '\'' +
                ", position=" + position +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", bir=" + bir +
                ", constellation='" + constellation + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
