package com.sqlitepatrical2.Model;

public class Model {
    String UserName,Age,NIC,Email;

    public Model(String userName, String age, String NIC, String email) {
        UserName = userName;
        Age = age;
        this.NIC = NIC;
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
