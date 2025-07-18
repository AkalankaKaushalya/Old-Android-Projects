package com.ak.unitycode.sl.Model;

public class UserModel {
    String Email, UserID, Name, Phone, Age, Paypal, UserType, Image, Coverimg, timesTamp;

    public UserModel() {
    }

    public UserModel(String email, String userID, String name, String phone, String age, String paypal, String userType, String image, String coverimg, String timesTamp) {
        Email = email;
        UserID = userID;
        Name = name;
        Phone = phone;
        Age = age;
        Paypal = paypal;
        UserType = userType;
        Image = image;
        Coverimg = coverimg;
        this.timesTamp = timesTamp;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getPaypal() {
        return Paypal;
    }

    public void setPaypal(String paypal) {
        Paypal = paypal;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCoverimg() {
        return Coverimg;
    }

    public void setCoverimg(String coverimg) {
        Coverimg = coverimg;
    }

    public String getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(String timesTamp) {
        this.timesTamp = timesTamp;
    }
}
