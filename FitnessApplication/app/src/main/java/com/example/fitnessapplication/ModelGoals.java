package com.example.fitnessapplication;

import android.graphics.Bitmap;

public class ModelGoals {
    private String name;
    private Bitmap image;

    public ModelGoals(){}

    public ModelGoals(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
