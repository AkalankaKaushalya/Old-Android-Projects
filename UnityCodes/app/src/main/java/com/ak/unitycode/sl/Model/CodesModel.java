package com.ak.unitycode.sl.Model;

public class CodesModel {
    String CodeID, CodeName, Codedescr, CodeUrl, Preview_img, viewsCount, downlodsCount, Likes, uid;
    long timestamp;

    public CodesModel() {
    }

    public CodesModel(String codeID, String codeName, String codedescr, String codeUrl, String preview_img, String viewsCount, String downlodsCount, String likes, String uid, long timestamp) {
        CodeID = codeID;
        CodeName = codeName;
        Codedescr = codedescr;
        CodeUrl = codeUrl;
        Preview_img = preview_img;
        this.viewsCount = viewsCount;
        this.downlodsCount = downlodsCount;
        Likes = likes;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public String getCodeID() {
        return CodeID;
    }

    public void setCodeID(String codeID) {
        CodeID = codeID;
    }

    public String getCodeName() {
        return CodeName;
    }

    public void setCodeName(String codeName) {
        CodeName = codeName;
    }

    public String getCodedescr() {
        return Codedescr;
    }

    public void setCodedescr(String codedescr) {
        Codedescr = codedescr;
    }

    public String getCodeUrl() {
        return CodeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        CodeUrl = codeUrl;
    }

    public String getPreview_img() {
        return Preview_img;
    }

    public void setPreview_img(String preview_img) {
        Preview_img = preview_img;
    }

    public String getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(String viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getDownlodsCount() {
        return downlodsCount;
    }

    public void setDownlodsCount(String downlodsCount) {
        this.downlodsCount = downlodsCount;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
