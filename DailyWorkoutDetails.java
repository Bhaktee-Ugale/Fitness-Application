package com.example.fitnessapp;

public class DailyWorkoutDetails {
    public static final String D_PERSON = "PERSON";
    public static final String D_WORKOUT = "WORKOUT";
    public static final String D_DATE = "DATE";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+DatabaseDetails.D_TABLE_NAME+"( "
            +D_DATE+" TEXT PRIMARY KEY, "
            +D_WORKOUT+" INTEGER, "
            +"FOREIGN KEY("+D_WORKOUT+") REFERENCES "+DatabaseDetails.W_TABLE_NAME+"("+Workouts.W_ID+");";
}
