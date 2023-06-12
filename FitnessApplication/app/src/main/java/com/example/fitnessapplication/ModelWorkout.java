package com.example.fitnessapplication;

import android.graphics.Bitmap;

public class ModelWorkout {
    private String id;
    private String name;
    private String info;
    private Bitmap image;

    public ModelWorkout(){}

    public ModelWorkout(String id, String name, String info ,Bitmap image) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
