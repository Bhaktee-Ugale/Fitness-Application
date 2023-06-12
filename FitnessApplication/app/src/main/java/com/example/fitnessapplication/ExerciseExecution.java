package com.example.fitnessapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseExecution#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseExecution extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String name, gif;

    private TextView exerciseName, exerciseCount, exerciseGif;

    private ImageView action;

    private View view;

    public ExerciseExecution() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseExecution.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseExecution newInstance(String param1, String param2) {
        ExerciseExecution fragment = new ExerciseExecution();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            gif = getArguments().getString(ARG_PARAM2);
        }

        exerciseCount = view.findViewById(R.id.exerciseTimer);
        exerciseName = view.findViewById(R.id.exerciseName);
        exerciseGif = view.findViewById(R.id.exerciseGif);
        action = view.findViewById(R.id.pausePlayBt);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        exerciseName.setText(name);
        Glide.with(view).load(gif).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(getActivity().getApplicationContext(), "Unable to load GIF due to unstable network...", Toast.LENGTH_SHORT);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exercise_execution, container, false);
        return view;
    }

    public void updateTime(String time){
        exerciseCount.setText(time);
    }

    public void updatePausePlay(String str){
        if(str.equals("pause")){
            action.setImageResource(R.drawable.ic_baseline_pause_24);
        }
        else if(str.equals("play")){
            action.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }
}