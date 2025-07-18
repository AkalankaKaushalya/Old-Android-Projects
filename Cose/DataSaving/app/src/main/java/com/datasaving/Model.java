package com.datasaving;

public class Model {
    String  nc, fn, ln, ma, cn, ad;

    public Model(String nc, String fn, String ln, String ma, String cn, String ad) {

        this.nc = nc;
        this.fn = fn;
        this.ln = ln;
        this.ma = ma;
        this.cn = cn;
        this.ad = ad;

    }


    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }
}

