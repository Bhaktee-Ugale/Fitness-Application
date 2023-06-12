package com.example.fitnessapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase ftdb;
    public static String DATABASE_PATH;
    private DecimalFormat df_obj = new DecimalFormat("#.#");



    public DbHelper(@Nullable Context context) {
        super(context, DatabaseDetails.DATABASE_NAME, null, DatabaseDetails.DATABASE_VERSION);
        this.context = context;
        this.DATABASE_PATH = context.getApplicationInfo().dataDir+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void createDataBase() throws IOException {
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if(databaseExist){
            // Do Nothing.
        }else{
            this.getWritableDatabase();
            copyDataBase();
        }// end if else dbExist
    }

    public boolean checkDataBase(){
        File databaseFile = new File(DATABASE_PATH + DatabaseDetails.DATABASE_NAME);
        return databaseFile.exists();
    }

    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DatabaseDetails.DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DatabaseDetails.DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DATABASE_PATH + DatabaseDetails.DATABASE_NAME;
        ftdb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if(ftdb != null)
            ftdb.close();
        super.close();
    }

    public void insertHealthDetails(String height, String weight, String date){
        openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HealthDetails.H_DATE_OF_RECORDING, date);
        contentValues.put(HealthDetails.H_HEIGHT, height);
        contentValues.put(HealthDetails.H_WEIGHT, weight);
        ftdb.insert(DatabaseDetails.H_TABLE_NAME, null, contentValues);
    }

    public boolean checkWhetherDateIsPresent(String date){
        openDataBase();
        boolean isP = false;
        String query = "SELECT "+HealthDetails.H_DATE_OF_RECORDING+" FROM "+DatabaseDetails.H_TABLE_NAME+" WHERE "+HealthDetails.H_DATE_OF_RECORDING+" = "+date+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(0).equals(date)){
                    isP = true;
                }
            }while (cursor.moveToNext());
        }
        return isP;
    }

    public ModelExercise getExercise(String id){
        openDataBase();
        ModelExercise modelExercise = new ModelExercise();
        String query = "SELECT * FROM "+DatabaseDetails.E_TABLE_NAME+" WHERE "+Exercises.E_ID+" = "+id+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                modelExercise.setName(cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_NAME)));
                modelExercise.setCount(cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_COUNT)));
                modelExercise.setGif(cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_GIF)));
                modelExercise.setCalories(cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_CALORIES)));
            }while(cursor.moveToNext());
        }
        return modelExercise;
    }

    public void updateHealthDetails(String date, String height, String weight){
        openDataBase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HealthDetails.H_HEIGHT, height);
        contentValues.put(HealthDetails.H_WEIGHT, weight);
        ftdb.update(DatabaseDetails.H_TABLE_NAME, contentValues, HealthDetails.H_DATE_OF_RECORDING+" =? ",new String[]{date});
    }

    public void updateRestCountdown(String rest, String countdown){
        openDataBase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("REST", rest);
        contentValues.put("COUNTDOWN", countdown);
        ftdb.update("RestCountdown", contentValues, "ID =? ", new String[]{"1"});

    }

    public void insertTodayWorkout(String exercise, String workout, String date, String timer){
        openDataBase();
        String calories = "";
        Log.d(" >> ", "insert workout "+exercise);
        String query = "SELECT * FROM "+DatabaseDetails.E_TABLE_NAME+" WHERE "+Exercises.E_ID+" = "+exercise+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        Log.d(" >> ", "insertTodayWorkout "+cursor.getCount());
        if(cursor.moveToFirst()){
            do {
                int cal = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_CALORIES))), tim = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_COUNT)));
                Log.d(" >> ", "calories inserttodayworkout "+cal);
                float cu = (Float.parseFloat(timer) * (float) cal) / (float) tim;
                calories = df_obj.format(cu);
            }while(cursor.moveToNext());
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(DailyWorkoutDetails.D_WORKOUT, workout);
        contentValues.put(DailyWorkoutDetails.D_EXERCISE, exercise);
        contentValues.put(DailyWorkoutDetails.D_CALORIES, calories);
        contentValues.put(DailyWorkoutDetails.D_TIME, date);
        contentValues.put(DailyWorkoutDetails.D_COUNT, timer);
        ftdb.insert(DatabaseDetails.D_TABLE_NAME, null, contentValues);
    }

    public void resetProgress(){
        openDataBase();
        String q1 = "DROP TABLE IF EXISTS DailyWorkoutDetails;";
        ftdb.execSQL(q1);
        String q2 = "CREATE TABLE DailyWorkoutDetails(\n" +
                "\"WORKOUT_ID\"\tINTEGER,\n" +
                "\"EXERCISE_ID\"\tINTEGER,\n" +
                "\"TIMER\"\tTEXT,\n" +
                "\"DATE_TIME\"\tTEXT,\n" +
                "\"CALORIES\"\tTEXT,\n" +
                "CONSTRAINT fk_WORKOUT_ID FOREIGN KEY(WORKOUT_ID) REFERENCES Workouts(ID),\n" +
                "CONSTRAINT fk_EXERCISE_ID FOREIGN KEY(EXERCISE_ID) REFERENCES Exercises(ID)\n" +
                ");";
        ftdb.execSQL(q2);
        //ftdb.delete(DatabaseDetails.D_TABLE_NAME, DailyWorkoutDetails.D_PERSON+ " =?", new String[]{id});
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

        return health;
    }

    public ModelRestCountdownTime getRestCountdownTime(){
        ModelRestCountdownTime rc = new ModelRestCountdownTime();
        openDataBase();

        String query = "SELECT * FROM "+DatabaseDetails.RC_TABLE_NAME+" WHERE ID = 1;";


        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToFirst()){
            rc = new ModelRestCountdownTime(
                    ""+cursor.getString(1),
                    ""+cursor.getString(2)
            );

            return rc;
        }


        return rc;
    }

    public ArrayList<String> getExerciseIds(String id){
        openDataBase();
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT * FROM "+DatabaseDetails.WE_TABLE_NAME+" WHERE WORKOUT_ID = "+id+";";
        Cursor cursor = ftdb.rawQuery(query, null);

        while(cursor.moveToNext()){
            String str = cursor.getString(2);
            list.add(str);
        }

        return list;
    }

    public ArrayList<ModelExercise> getExercises(String id){
        openDataBase();
        ArrayList<ModelExercise> modelExercises = new ArrayList<>();
        ArrayList<String> ids = getExerciseIds(id);
        Log.d(" >> ", ""+ids.size());
        for(String ID: ids){
            String query = "SELECT * FROM "+DatabaseDetails.E_TABLE_NAME+" WHERE "+Exercises.E_ID+" = "+ID+";";
            Cursor cursor = ftdb.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    ModelExercise modelExercise = new ModelExercise(
                            ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_ID)),
                            ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_NAME)),
                            ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_COUNT)),
                            ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_CALORIES)),
                            ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_INFO)),
                            ""+cursor.getString(cursor.getColumnIndexOrThrow(Exercises.E_GIF))
                    );
                    modelExercises.add(modelExercise);
                }while (cursor.moveToNext());
            }
        }
        return modelExercises;
    }

    public ArrayList<ModelWorkoutHistory> getWorkoutHistory(){
        openDataBase();
        ArrayList<ModelWorkoutHistory> modelWorkoutHistoryArrayList = new ArrayList<>();
        String query = "SELECT * FROM "+DatabaseDetails.D_TABLE_NAME+" ORDER BY "+DailyWorkoutDetails.D_TIME+" DESC;";
        Cursor cursor = ftdb.rawQuery(query, null);
        Log.d(" >> ", " size of cursor: "+cursor.getCount());
        if(cursor.moveToFirst()){
            do{
                ModelWorkoutHistory modelWorkoutHistory = new ModelWorkoutHistory();
                modelWorkoutHistory.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_TIME)));
                modelWorkoutHistory.setCalories_burned(cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_CALORIES)));
                modelWorkoutHistory.setTime_spent(cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_COUNT)));
                String q1 = "SELECT "+Exercises.E_NAME+" FROM "+DatabaseDetails.E_TABLE_NAME+" WHERE "+Exercises.E_ID+" = "+cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_EXERCISE))+";";
                String q2 = "SELECT "+Workouts.W_NAME+" FROM "+DatabaseDetails.W_TABLE_NAME+" WHERE "+Workouts.W_ID+" = "+cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_WORKOUT))+";";
                Cursor c1 = ftdb.rawQuery(q1, null);
                Log.d(" >> ", "size of c1: "+ c1.getCount());
                if(c1.moveToFirst()){
                    do{
                        modelWorkoutHistory.setExercise_name(c1.getString(c1.getColumnIndexOrThrow(Exercises.E_NAME)));
                    }while(c1.moveToNext());
                }
                Cursor c2 = ftdb.rawQuery(q2, null);
                Log.d(" >> ", "size of c2: "+ c2.getCount());
                if(c2.moveToFirst()){
                    do {
                        modelWorkoutHistory.setWorkout_name(c2.getString(c2.getColumnIndexOrThrow(Workouts.W_NAME)));
                    }while(c2.moveToNext());
                }
                modelWorkoutHistoryArrayList.add(modelWorkoutHistory);

            }while(cursor.moveToNext());
        }
        return modelWorkoutHistoryArrayList;
    }

    public ModelGoals getGoalDetails(String id){
        openDataBase();
        ModelGoals modelGoal = new ModelGoals();
        String query = "SELECT * FROM "+DatabaseDetails.G_TABLE_NAME+" WHERE ID = "+id+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToNext()){
            byte[] image = cursor.getBlob(2);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            modelGoal = new ModelGoals(
                    ""+cursor.getString(1),
                    bmp
            );
        }
        return modelGoal;
    }

    public int getMinutes(){
        openDataBase();
        float total = 0;
        String query = "SELECT * "+" FROM "+DatabaseDetails.D_TABLE_NAME+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total += Float.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_COUNT)));
            }while(cursor.moveToNext());
        }
        return (int)total;
    }

    public int getCalories(){
        openDataBase();
        float total = 0;
        String query = "SELECT * "+" FROM "+DatabaseDetails.D_TABLE_NAME+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                total += Float.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DailyWorkoutDetails.D_CALORIES)));
            }while(cursor.moveToNext());
        }
        return (int)total;
    }

    public int getWorkoutcount(){
        openDataBase();
        String query = "SELECT DISTINCT "+DailyWorkoutDetails.D_WORKOUT+" FROM "+DatabaseDetails.D_TABLE_NAME+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        return cursor.getCount();
    }

    public ModelWorkout getWorkoutDetails(String id){
        openDataBase();
        ModelWorkout modelWorkout = new ModelWorkout();
        String query = "SELECT * FROM "+DatabaseDetails.W_TABLE_NAME+" WHERE "+Workouts.W_ID+"= "+id+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        if(cursor.moveToNext()){
            byte[] image = cursor.getBlob(3);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            modelWorkout = new ModelWorkout(
                    ""+cursor.getString(cursor.getColumnIndexOrThrow(Workouts.W_ID)),
                    ""+cursor.getString(1),
                    ""+cursor.getString(2),
                    bmp
            );
        }
        return modelWorkout;
    }

    private ArrayList<String> getWorkoutIds(String id){
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT * FROM "+DatabaseDetails.GW_TABLE_NAME+" WHERE GOAL_ID = "+id+";";
        Cursor cursor = ftdb.rawQuery(query, null);
        while(cursor.moveToNext()){
            String str = cursor.getString(1);
            list.add(str);
        }

        return list;
    }

    public ArrayList<ModelWorkout> getWorkouts(String id){
        openDataBase();
        ArrayList<ModelWorkout> modelWorkouts = new ArrayList<>();
        ArrayList<String> ids = getWorkoutIds(id);
        for(String ID: ids){
            String query = "SELECT * FROM "+DatabaseDetails.W_TABLE_NAME+" WHERE "+Workouts.W_ID+" = "+ID+";";
            Cursor cursor = ftdb.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    byte[] image = cursor.getBlob(3);
                    Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                    ModelWorkout modelWorkout = new ModelWorkout(
                            ""+cursor.getInt(0),
                            ""+cursor.getString(1),
                            ""+cursor.getString(2),
                            bmp
                    );
                    modelWorkouts.add(modelWorkout);
                }while (cursor.moveToNext());
            }
        }
        return modelWorkouts;
    }



    public String addReminder(String title, String date, String time){
        openDataBase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Remainder.R_TITLE,title);
        contentValues.put(Remainder.R_DATE,date);
        contentValues.put(Remainder.R_TIME,time);

        float result = ftdb.insert(DatabaseDetails.R_TABLE_NAME, null, contentValues);

        if(result == -1){
            return "Failed";
        } else {
            return "Successfully inserted";
        }

    }
    public ArrayList<ModelRemainder> readAllReminders() {
        openDataBase();
        ArrayList<ModelRemainder> remainders = new ArrayList<>();
        String query = "select * from "+DatabaseDetails.R_TABLE_NAME+ " order by "+ Remainder.R_ID +" desc;";                               //Sql query to  retrieve  data from the database
        Cursor cursor = ftdb.rawQuery(query, null);
        while (cursor.moveToNext()) {
            ModelRemainder model = new ModelRemainder(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            remainders.add(model);
        }

        return remainders;
    }

    public void deleteRemainder(String id){
        openDataBase();
        ftdb.delete(DatabaseDetails.R_TABLE_NAME, Remainder.R_ID+" =? ", new String[]{id});

    }

    public ModelHealth getRecentHealthDetails(){
        ArrayList<ModelHealth> modelHealths = getHealthDetails();
        if(modelHealths.size()>0) return modelHealths.get(modelHealths.size()-1);
        return new ModelHealth("", "", "");
    }

}
