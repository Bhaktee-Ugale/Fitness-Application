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

public class AdapterWorkout extends RecyclerView.Adapter<AdapterWorkout.WorkoutViewHolder> {

    private Context context;
    private ArrayList<ModelWorkout> workoutList;
    private DbHelper dbHelper;

    public AdapterWorkout(Context context, ArrayList<ModelWorkout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_single_workout, parent, false);
        WorkoutViewHolder vh = new WorkoutViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        ModelWorkout modelWorkout = workoutList.get(position);

        String id = modelWorkout.getId();
        String name = modelWorkout.getName();
        String info = modelWorkout.getInfo();
        Bitmap image = modelWorkout.getImage();

        holder.titleTv.setText(name);
        holder.descriptionTv.setText(info);
        holder.imageIv.setImageBitmap(image);

        holder.startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseHome.class);
                intent.putExtra("ID", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv, descriptionTv;
        Button startBt;
        ImageView imageIv;
        RelativeLayout relativeLayout;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            startBt = itemView.findViewById(R.id.bt1);
            imageIv = itemView.findViewById(R.id.imageIv);
            relativeLayout = itemView.findViewById(R.id.relativeLy);
        }
    }
}
