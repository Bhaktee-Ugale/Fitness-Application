package com.example.fitnessapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterExercise extends RecyclerView.Adapter<AdapterExercise.ExerciseViewHolder> {

    private Context context;
    private ArrayList<ModelExercise> exerciseList;
    private DbHelper dbHelper;

    public AdapterExercise(Context context, ArrayList<ModelExercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_exercise, parent, false);
        ExerciseViewHolder vh = new ExerciseViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ModelExercise modelExercise = exerciseList.get(position);

        String id = modelExercise.getId();
        String name = modelExercise.getName();
        String info = modelExercise.getInfo();
        String count = modelExercise.getCount();


        holder.titleTv.setText(name);
        holder.countTv.setText(count+" s");

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(name, info);
            }
        });
    }

    public void show(String name, String info){
        final Dialog d = new Dialog(context, R.style.Dialog);
        d.setTitle(name);
        d.setContentView(R.layout.exercise_dialog);
        Button close = (Button) d.findViewById(R.id.closeBt);
        TextView des = (TextView) d.findViewById(R.id.descriptionDb);
        des.setText(info);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv, countTv;
        LinearLayout linearLayout;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.exerciseTitleTv);
            countTv = itemView.findViewById(R.id.timerTv);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
