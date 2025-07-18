package com.myschool.Models;

public class TNotesModel {
    String uid, Title, Subject, NID;
    long Date;

    public TNotesModel() {
    }

    public TNotesModel(String uid, String title, String subject, String NID, long date) {
        this.uid = uid;
        Title = title;
        Subject = subject;
        this.NID = NID;
        Date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }
}
