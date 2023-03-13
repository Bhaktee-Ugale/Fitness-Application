package com.example.fitnessapp;

public class HealthDetails {
    public static final String H_HEIGHT = "HEIGHT";
    public static final String H_WEIGHT = "WEIGHT";
    public static final String H_DATE_OF_RECORDING = "DATE_OF_RECORDING";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+DatabaseDetails.H_TABLE_NAME+"( "
            +H_DATE_OF_RECORDING+" TEXT, "
            +H_HEIGHT+" TEXT, "
            +H_WEIGHT+" TEXT;";
}
