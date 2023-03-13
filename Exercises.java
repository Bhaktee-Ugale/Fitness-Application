package com.example.fitnessapp;

public class Exercises {
    public static final String E_ID = "ID";
    public static final String E_NAME = "NAME";
    public static final String E_INFO = "INFO";
    public static final String E_COUNT = "COUNT";
    public static final String E_CALORIES = "CALORIES";
    public static final String E_GIF = "GIF";

    public static final String CREATE_TABLE = "CREATE TABLE "+DatabaseDetails.E_TABLE_NAME+"( "
            +E_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +E_NAME+" TEXT, "
            +E_INFO+" TEXT, "
            +E_CALORIES+" TEXT, "
            +E_COUNT+" TEXT "
            +" );";
}