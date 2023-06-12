package com.example.fitnessapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRemainder extends RecyclerView.Adapter<AdapterRemainder.RemainderViewHolder> {
    ArrayList<ModelRemainder> dataholder;

    private DbHelper dbHelper;
    private Context context;
    private RemainderViewHolder holder;
    private int position;

    public AdapterRemainder(ArrayList<ModelRemainder> dataholder, Context context) {
        this.dataholder = dataholder;
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public RemainderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_remainder_cardview, parent, false);
        return new RemainderViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RemainderViewHolder holder, int position) {
        ModelRemainder modelRemainder = dataholder.get(position);
        holder.mTitle.setText(modelRemainder.getTitle());
        holder.mDate.setText(modelRemainder.getDate());
        holder.mTime.setText(modelRemainder.getTime());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteRemainder(modelRemainder.getId());
                Toast.makeText(context, "Deleted Successfully...", Toast.LENGTH_SHORT).show();
                ((RemainderPage)context).onResume();
            }
        });
    }

    public int getItemCount() {
        return dataholder.size();
    }

    class RemainderViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mDate, mTime;
        ImageView delete;

        public RemainderViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            mDate = (TextView) itemView.findViewById(R.id.txtDate);
            mTime = (TextView) itemView.findViewById(R.id.txtTime);
            delete = (ImageView) itemView.findViewById(R.id.img1);
        }
    }

}