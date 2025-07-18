package com.ak.unitycode.sl.Model;

public class LessonModel {
    String Lid, views, imageurl, videourl, videotitle, videodecrip, likes, uid;

    public LessonModel() {
    }

    public LessonModel(String lid, String views, String imageurl, String videourl, String videotitle, String videodecrip, String likes, String uid) {
        Lid = lid;
        this.views = views;
        this.imageurl = imageurl;
        this.videourl = videourl;
        this.videotitle = videotitle;
        this.videodecrip = videodecrip;
        this.likes = likes;
        this.uid = uid;
    }

    public String getLid() {
        return Lid;
    }

    public void setLid(String lid) {
        Lid = lid;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
    }

    public String getVideodecrip() {
        return videodecrip;
    }

    public void setVideodecrip(String videodecrip) {
        this.videodecrip = videodecrip;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

