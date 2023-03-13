package com.example.fitnessapp;

public class Workouts {
    public static final String W_ID = "ID";
    private static final String W_NAME = "NAME";
    private static final String W_INFO = "INFO";

    public static final String CREATE_TABLE = "CREATE TABLE "+DatabaseDetails.W_TABLE_NAME+"( "
            +W_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +W_NAME+" TEXT, "
            +W_INFO+" TEXT"
            +" );";
}
