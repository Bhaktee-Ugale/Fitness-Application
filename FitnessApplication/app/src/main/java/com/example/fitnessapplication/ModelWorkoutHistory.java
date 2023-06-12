package com.example.fitnessapplication;

public class ModelWorkoutHistory {
    private String workout_name, exercise_name, time_spent, calories_burned, date;

    public ModelWorkoutHistory(){}

    public ModelWorkoutHistory(String workout_name, String exercise_name, String time_spent, String calories_burned, String date) {
        this.workout_name = workout_name;
        this.exercise_name = exercise_name;
        this.time_spent = time_spent;
        this.calories_burned = calories_burned;
        this.date = date;
    }

    public String getWorkout_name() {
        return workout_name;
    }

    public void setWorkout_name(String workout_name) {
        this.workout_name = workout_name;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(String time_spent) {
        this.time_spent = time_spent;
    }

    public String getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(String calories_burned) {
        this.calories_burned = calories_burned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
