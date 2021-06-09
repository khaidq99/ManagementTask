package com.example.baitapcuoiky.model;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String title;
    private String des;
    private String date;
    private int status;
    private User user;

    public Task() {
    }

    public Task(String title, String des, String date, int status, User user) {
        this.title = title;
        this.des = des;
        this.date = date;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
