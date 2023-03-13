package com.example.fitnessapp;

public class PersonalDetails {
    public static final String P_ID = "ID";
    public static final String P_NAME = "NAME";
    public static final String P_PHONE = "PHONE";
    public static final String P_GENDER = "GENDER";
    public static final String P_BIRTHDAY = "BIRTHDAY";
    public static final String P_REST_TIME = "REST_TIME";
    public static final String P_COUNTDOWN_TIME = "COUNTDOWN_TIME";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+DatabaseDetails.P_TABLE_NAME+"( "
            +P_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +P_NAME+" TEXT, "
            +P_PHONE+" TEXT, "
            +P_GENDER+" TEXT, "
            +P_BIRTHDAY+" TEXT, "
            +P_REST_TIME+" TEXT, "
            +P_COUNTDOWN_TIME+" TEXT"
            +" );";

}
