package com.courseapp.pvt.Model;

public class SubjectModel {
    String User, Subject, Message;

    public SubjectModel(String user, String subject, String message) {
        User = user;
        Subject = subject;
        Message = message;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
