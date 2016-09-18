package com.example.sai_praneeth7777.hasportal.Objects;

/**
 * Created by sai_praneeth7777 on 29-Jun-16.
 */
public class ChatObject {
    private String body,time,date,user;

    public ChatObject(String body, String time,String date,String user) {
        this.setBody(body);
        this.setTime(time);
        this.setUser(user);
        this.setDate(date);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(String user){ this.user = user;}

    public String getBody() {
        return body;
    }
    public String getTime() {
        return time;
    }
    public String getUser() {
        return user;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate(){return date;}
}
