package com.myschool.Models;

public class UsersModel {
    String uid, name, email, phone, userType;


    public UsersModel() {

    }

    public UsersModel(String uid, String name, String email, String phone, String userType) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
