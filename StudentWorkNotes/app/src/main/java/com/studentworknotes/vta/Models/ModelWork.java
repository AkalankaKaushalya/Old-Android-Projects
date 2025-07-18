package com.studentworknotes.vta.Models;

public class ModelWork {
    String title, maseeag;

    public ModelWork(String title, String maseeag) {
        this.title = title;
        this.maseeag = maseeag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaseeag() {
        return maseeag;
    }

    public void setMaseeag(String maseeag) {
        this.maseeag = maseeag;
    }
}
