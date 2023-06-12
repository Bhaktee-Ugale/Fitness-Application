package com.example.fitnessapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterWorkoutHistory extends RecyclerView.Adapter<AdapterWorkoutHistory.WorkoutHistoryViewHolder> {

    private Context context;
    private ArrayList<ModelWorkoutHistory> workoutHistoryList;
    private DbHelper dbHelper;

    public AdapterWorkoutHistory(Context context, ArrayList<ModelWorkoutHistory> workoutHistoryList) {
        this.context = context;
        this.workoutHistoryList = workoutHistoryList;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public WorkoutHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_exercise_history, parent, false);
        WorkoutHistoryViewHolder vh = new WorkoutHistoryViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHistoryViewHolder holder, int position) {
        ModelWorkoutHistory modelWorkoutHistory = workoutHistoryList.get(position);
        holder.workoutTv.setText(modelWorkoutHistory.getWorkout_name());
        holder.exerciseTv.setText(modelWorkoutHistory.getExercise_name());
        holder.calories.setText(modelWorkoutHistory.getCalories_burned()+" cal");
        holder.date.setText(modelWorkoutHistory.getDate());
        holder.time_spent.setText("00:"+modelWorkoutHistory.getTime_spent());
    }

    @Override
    public int getItemCount() {
        return workoutHistoryList.size();
    }

    class WorkoutHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView workoutTv, exerciseTv, time_spent, calories, date;


        public WorkoutHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            workoutTv = itemView.findViewById(R.id.workoutNameTv);
            exerciseTv = itemView.findViewById(R.id.exerciseNameTv);
            time_spent = itemView.findViewById(R.id.timeSpent);
            calories = itemView.findViewById(R.id.caloriesBurned);
            date = itemView.findViewById(R.id.date);
        }
    }
}
