package com.example.fitnessapplication;

public class ModelExercise {
    private String id;
    private String name;
    private String count;
    private String calories;
    private String info;
    private String gif;

    public ModelExercise(){}

    public ModelExercise(String id, String name, String count, String calories, String info, String gif){
        this.id = id;
        this.name = name;
        this.count = count;
        this.calories = calories;
        this.info = info;
        this.gif = gif;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getGif() {
        return gif;
    }

    public void setGif(String gif) {
        this.gif = gif;
    }
}
