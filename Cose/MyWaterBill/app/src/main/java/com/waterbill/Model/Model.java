package com.waterbill.Model;

public class Model {
    String Bid, TMoun, TMun, Useunit, Totle;

    public Model(String bid, String TMoun, String TMun, String useunit, String totle) {
        Bid = bid;
        this.TMoun = TMoun;
        this.TMun = TMun;
        Useunit = useunit;
        Totle = totle;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
    }

    public String getTMoun() {
        return TMoun;
    }

    public void setTMoun(String TMoun) {
        this.TMoun = TMoun;
    }

    public String getTMun() {
        return TMun;
    }

    public void setTMun(String TMun) {
        this.TMun = TMun;
    }

    public String getUseunit() {
        return Useunit;
    }

    public void setUseunit(String useunit) {
        Useunit = useunit;
    }

    public String getTotle() {
        return Totle;
    }

    public void setTotle(String totle) {
        Totle = totle;
    }
}
