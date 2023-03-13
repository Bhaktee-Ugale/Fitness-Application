package com.example.fitnessapp;

public class WorkoutsExcercises {
    public static final String WE_WORKOUT = "WORKOUT";
    public static final String WE_EXERCISE = "EXERCISE";

    public static final String CREATE_TABLE = "CREATE TABLE "+DatabaseDetails.D_TABLE_NAME+"( "
            +WE_WORKOUT+" INTEGER, "
            +WE_EXERCISE+" INTEGER, "
            +"FOREIGN KEY("+WE_WORKOUT+") REFERENCES "+DatabaseDetails.W_TABLE_NAME+"("+Workouts.W_ID+"), "
            +"FOREIGN KEY("+WE_EXERCISE+") REFERENCES "+DatabaseDetails.E_TABLE_NAME+"("+Exercises.E_ID+")"
            +";";
}
