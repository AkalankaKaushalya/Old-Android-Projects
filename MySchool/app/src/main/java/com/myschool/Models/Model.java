package com.myschool.Models;

public class Model {
    int ID;
    String Title, Masseage, NDate;

    public Model() {
    }

    public Model(int ID, String title, String masseage, String NDate) {
        this.ID = ID;
        Title = title;
        Masseage = masseage;
        this.NDate = NDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMasseage() {
        return Masseage;
    }

    public void setMasseage(String masseage) {
        Masseage = masseage;
    }

    public String getNDate() {
        return NDate;
    }

    public void setNDate(String NDate) {
        this.NDate = NDate;
    }
}
