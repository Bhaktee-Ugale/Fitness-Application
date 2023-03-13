package com.example.fitnessapp;

public class Remainder {
    public static final String R_ID = "ID";
    public static final String R_TITLE = "TITLE";
    public static final String R_DATE = "DATE";
    public static final String R_TIME = "TIME";

    public static final String CREATE_TABLE = "CREATE TABLE "+DatabaseDetails.R_TABLE_NAME+"( "
            +R_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +R_TITLE+" TEXT, "
            +R_DATE+" TEXT, "
            +R_TIME+" TEXT "
            +" );";
}