package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase ftdb;



    public DbHelper(@Nullable Context context) {
        super(context, DatabaseDetails.DATABASE_NAME, null, DatabaseDetails.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    @Override
    public synchronized void close(){
        if(ftdb!=null){
            ftdb.close();
        }
        super.close();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DatabaseDetails.DATABASE_PATH + DatabaseDetails.DATABASE_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DatabaseDetails.DATABASE_NAME);
            String outputFileName = DatabaseDetails.DATABASE_PATH + DatabaseDetails.DATABASE_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }

    }

    public void openDataBase() throws SQLException {
        String myPath = DatabaseDetails.DATABASE_PATH + DatabaseDetails.DATABASE_NAME;
        ftdb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }



    public long insertPersonalDetails(String name, String phone, String gender, String birthday, String countdown, String rest){
        openDataBase();
        ftdb.execSQL(PersonalDetails.CREATE_TABLE);

        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonalDetails.P_NAME, name);
        contentValues.put(PersonalDetails.P_PHONE, phone);
        contentValues.put(PersonalDetails.P_GENDER, gender);
        contentValues.put(PersonalDetails.P_BIRTHDAY, birthday);
        contentValues.put(PersonalDetails.P_REST_TIME, rest);
        contentValues.put(PersonalDetails.P_COUNTDOWN_TIME, countdown);

        long id = ftdb.insert(DatabaseDetails.P_TABLE_NAME, null, contentValues);
        ftdb.close();
        return id;
    }

    public void insertHealthDetails(String height, String weight, String date){
        openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HealthDetails.H_HEIGHT, height);
        contentValues.put(HealthDetails.H_WEIGHT, weight);
        contentValues.put(HealthDetails.H_DATE_OF_RECORDING, date);
        ftdb.insert(DatabaseDetails.H_TABLE_NAME, null, contentValues);
        ftdb.close();
    }

    public void updatePersonalDetails(String id, String name, String phone, String gender, String birthday){
        openDataBase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonalDetails.P_NAME, name);
        contentValues.put(PersonalDetails.P_PHONE, phone);
        contentValues.put(PersonalDetails.P_GENDER, gender);
        contentValues.put(PersonalDetails.P_BIRTHDAY, birthday);

        ftdb.update(DatabaseDetails.P_TABLE_NAME, contentValues, PersonalDetails.P_ID+" =? ", new String[]{id});
        ftdb.close();
    }

    public void updateRestCountdown(String id, String rest, String countdown){
        openDataBase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PersonalDetails.P_REST_TIME, rest);
        contentValues.put(PersonalDetails.P_COUNTDOWN_TIME, countdown);
        ftdb.update(DatabaseDetails.P_TABLE_NAME, contentValues, PersonalDetails.P_ID+" =? ", new String[]{id});
        ftdb.close();
    }

    public void insertTodayWorkout(String id, String workout, String date){

    }

    public void deleteDailyWorkoutsDetails(String id){
        openDataBase();
        ftdb.delete(DatabaseDetails.D_TABLE_NAME, DailyWorkoutDetails.D_PERSON+ " =?", new String[]{id});
        ftdb.close();
    }

    public ArrayList<ModelHealth> getHealthDetails(){
        ArrayList<ModelHealth> health = new ArrayList<>();
        openDataBase();
        String query = "SELECT * FROM "+DatabaseDetails.H_TABLE_NAME+";";

        Cursor cursor = ftdb.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ModelHealth userHealth = new ModelHealth(
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(HealthDetails.H_HEIGHT)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(HealthDetails.H_WEIGHT)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(HealthDetails.H_DATE_OF_RECORDING))
                );
                health.add(userHealth);
            }while(cursor.moveToNext());
        }
        ftdb.close();

        return health;
    }

    public ModelPersonal getPersonalDetails(String id){
        ModelPersonal user = new ModelPersonal();
        openDataBase();

        String query = "SELECT * FROM "+DatabaseDetails.P_TABLE_NAME+" WHERE "+PersonalDetails.P_ID+" = "+id+";";

        Cursor cursor = ftdb.rawQuery(query, null);

        if(cursor.moveToFirst()){
            user = new ModelPersonal(
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(PersonalDetails.P_NAME)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(PersonalDetails.P_PHONE)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(PersonalDetails.P_BIRTHDAY)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(PersonalDetails.P_GENDER))
            );
            return user;
        }

        return user;
    }

    public ModelRestCountdownTime getRestCountdownTime(String id){
        ModelRestCountdownTime rc = new ModelRestCountdownTime();
        openDataBase();

        String query = "SELECT * FROM "+DatabaseDetails.P_TABLE_NAME+" WHERE "+PersonalDetails.P_ID+" = "+id+";";


        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToFirst()){
            rc = new ModelRestCountdownTime(
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(PersonalDetails.P_REST_TIME)),
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(PersonalDetails.P_COUNTDOWN_TIME))
            );
            ftdb.close();
            return rc;
        }

        ftdb.close();

        return rc;
    }

    public ArrayList<ModelExercise> getExercise(){
        ArrayList<ModelExercise> exercise = new ArrayList<>();
        openDataBase();

        String query = "SELECT * FROM "+DatabaseDetails.E_TABLE_NAME;

        Cursor cursor = ftdb.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ModelExercise exercise1 = new ModelExercise(
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_COUNT)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_CALORIES)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_INFO)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_GIF))
                );
                exercise.add(exercise1);
            }while(cursor.moveToNext());
        }

        ftdb.close();


        return exercise;
    }

    public String addReminder(String title, String date, String time){
        openDataBase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Remainder.R_TITLE,title);
        contentValues.put(Remainder.R_DATE,date);
        contentValues.put(Remainder.R_TIME,time);

        float result = ftdb.insert(DatabaseDetails.R_TABLE_NAME, null, contentValues);
        ftdb.close();
        if(result == -1){
            return "Failed";
        } else {
            return "Successfully inserted";
        }

    }
    public ArrayList<ModelRemainder> readAllReminders() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<ModelRemainder> remainders = new ArrayList<>();
        String query = "select * from "+DatabaseDetails.R_TABLE_NAME+ " order by "+ Remainder.R_ID +" desc ";                               //Sql query to  retrieve  data from the database
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            ModelRemainder model = new ModelRemainder(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            remainders.add(model);
        }

        return remainders;
    }

    public void deleteRemainder(String id){
        openDataBase();
        ftdb.delete(DatabaseDetails.R_TABLE_NAME, Remainder.R_ID+" =? ", new String[]{id});
        ftdb.close();
    }

}
