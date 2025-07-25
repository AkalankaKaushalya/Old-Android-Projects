package com.will_dev.vpn_app.fromanother.item;

import java.io.Serializable;

public class ProfileList implements Serializable {

    private String user_id,user_name,user_email,user_phone,is_verified,user_image, total_status,user_youtube,user_instagram,user_code,total_point,total_followers,total_following,already_follow;

    public ProfileList(String user_id, String user_name, String user_email, String user_phone, String is_verified, String user_image, String total_status, String user_youtube, String user_instagram, String user_code, String total_point, String total_followers, String total_following, String already_follow) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.is_verified = is_verified;
        this.user_image = user_image;
        this.total_status = total_status;
        this.user_youtube = user_youtube;
        this.user_instagram = user_instagram;
        this.user_code = user_code;
        this.total_point = total_point;
        this.total_followers = total_followers;
        this.total_following = total_following;
        this.already_follow = already_follow;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getTotal_status() {
        return total_status;
    }

    public void setTotal_status(String total_status) {
        this.total_status = total_status;
    }

    public String getUser_youtube() {
        return user_youtube;
    }

    public void setUser_youtube(String user_youtube) {
        this.user_youtube = user_youtube;
    }

    public String getUser_instagram() {
        return user_instagram;
    }

    public void setUser_instagram(String user_instagram) {
        this.user_instagram = user_instagram;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getTotal_point() {
        return total_point;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }

    public String getTotal_followers() {
        return total_followers;
    }

    public void setTotal_followers(String total_followers) {
        this.total_followers = total_followers;
    }

    public String getTotal_following() {
        return total_following;
    }

    public void setTotal_following(String total_following) {
        this.total_following = total_following;
    }

    public String getAlready_follow() {
        return already_follow;
    }

    public void setAlready_follow(String already_follow) {
        this.already_follow = already_follow;
    }

}
