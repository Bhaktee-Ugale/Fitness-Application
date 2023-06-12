package com.example.fitnessapplication;

public class ModelRestCountdownTime {
    private String rest;
    private String countdown;

    public ModelRestCountdownTime(){}

    public ModelRestCountdownTime(String rest, String countdown){
        this.rest = rest;
        this.countdown = countdown;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }
}
