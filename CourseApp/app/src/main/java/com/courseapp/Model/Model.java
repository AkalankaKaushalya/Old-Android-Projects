package com.courseapp.Model;

public class Model {
    String Subject, Message, User;

    public Model(String subject, String message, String user) {
        Subject = subject;
        Message = message;
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

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
