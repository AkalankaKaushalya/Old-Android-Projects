package com.electricalmeterbile;

public class Model {

    String BilNo, lbdate, nbdate, lunit, nunit, Nuseunit, oneUnitP, ThisMonPris;

    public Model(String bilNo, String lbdate, String nbdate, String lunit, String nunit, String nuseunit, String oneUnitP, String thisMonPris) {
        BilNo = bilNo;
        this.lbdate = lbdate;
        this.nbdate = nbdate;
        this.lunit = lunit;
        this.nunit = nunit;
        Nuseunit = nuseunit;
        this.oneUnitP = oneUnitP;
        ThisMonPris = thisMonPris;
    }

    public String getBilNo() {
        return BilNo;
    }

    public void setBilNo(String bilNo) {
        BilNo = bilNo;
    }

    public String getLbdate() {
        return lbdate;
    }

    public void setLbdate(String lbdate) {
        this.lbdate = lbdate;
    }

    public String getNbdate() {
        return nbdate;
    }

    public void setNbdate(String nbdate) {
        this.nbdate = nbdate;
    }

    public String getLunit() {
        return lunit;
    }

    public void setLunit(String lunit) {
        this.lunit = lunit;
    }

    public String getNunit() {
        return nunit;
    }

    public void setNunit(String nunit) {
        this.nunit = nunit;
    }

    public String getNuseunit() {
        return Nuseunit;
    }

    public void setNuseunit(String nuseunit) {
        Nuseunit = nuseunit;
    }

    public String getOneUnitP() {
        return oneUnitP;
    }

    public void setOneUnitP(String oneUnitP) {
        this.oneUnitP = oneUnitP;
    }

    public String getThisMonPris() {
        return ThisMonPris;
    }

    public void setThisMonPris(String thisMonPris) {
        ThisMonPris = thisMonPris;
    }
}
