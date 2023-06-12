package com.example.fitnessapplication;

public class ModelRemainder {
    private String title, date, time, id;

    public ModelRemainder() {
    }
    public ModelRemainder(String id, String title, String date, String time) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
