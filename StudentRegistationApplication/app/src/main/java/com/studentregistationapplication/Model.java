package com.studentregistationapplication;

public class Model {
    String Title,Subje;

    public Model(String title, String subje) {
        Title = title;
        Subje = subje;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubje() {
        return Subje;
    }

    public void setSubje(String subje) {
        Subje = subje;
    }
}
