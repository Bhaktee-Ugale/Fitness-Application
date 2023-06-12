package com.example.fitnessapplication;

public class ModelHealth {
    private String height;
    private String weight;
    private String date;

    public ModelHealth(){}

    public ModelHealth(String height, String weight, String date){
        this.height = height;
        this.weight = weight;
        this.date = date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
