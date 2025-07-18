package com.myschool.Models;

public class ChatModel {
    String uid, Massages, ProfileImg, chatId, Uname;
    long Time;

    public ChatModel() {
    }

    public ChatModel(String uid, String massages, String profileImg, String chatId, String uname, long time) {
        this.uid = uid;
        Massages = massages;
        ProfileImg = profileImg;
        this.chatId = chatId;
        Uname = uname;
        Time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMassages() {
        return Massages;
    }

    public void setMassages(String massages) {
        Massages = massages;
    }

    public String getProfileImg() {
        return ProfileImg;
    }

    public void setProfileImg(String profileImg) {
        ProfileImg = profileImg;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }
}
