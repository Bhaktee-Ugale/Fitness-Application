package com.example.fitnessapp;

public class ModelPersonal {
    String name;
    String phone;
    String bdate;
    String gender;

    ModelPersonal(){}

    ModelPersonal(String name, String phone, String bdate, String gender){
        this.name = name;
        this.phone = phone;
        this.bdate = bdate;
        this.gender = gender;
    }
}
