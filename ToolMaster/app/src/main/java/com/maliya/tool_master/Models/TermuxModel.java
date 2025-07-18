package com.maliya.tool_master.Models;

public class TermuxModel {
    String uid, toolid, title, commend, description, tooltype, url;
    int viewCount;
    long timestamp;

    public TermuxModel() {
    }

    public TermuxModel(String uid, String toolid, String title, String commend, String description, String tooltype, String url, int viewCount, long timestamp) {
        this.uid = uid;
        this.toolid = toolid;
        this.title = title;
        this.commend = commend;
        this.description = description;
        this.tooltype = tooltype;
        this.url = url;
        this.viewCount = viewCount;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToolid() {
        return toolid;
    }

    public void setToolid(String toolid) {
        this.toolid = toolid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommend() {
        return commend;
    }

    public void setCommend(String commend) {
        this.commend = commend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTooltype() {
        return tooltype;
    }

    public void setTooltype(String tooltype) {
        this.tooltype = tooltype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
