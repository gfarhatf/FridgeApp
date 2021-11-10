package com.example.fridgeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import static com.example.fridgeapp.R.layout.row;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    public ArrayList<String> list;

    public MyAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        String res = list.get(position);
        holder.sensorNameTextView.setText(res);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RecyclerView myRecycler;
        public LinearLayout myLayout;
        public TextView sensorNameTextView;
        String sensorName = "";
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            myRecycler = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            sensorNameTextView = (TextView) itemView.findViewById(R.id.ingredientNameEntry);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            TextView clickedTv = (TextView) view.findViewById(R.id.ingredientNameEntry);
            sensorName = (String) clickedTv.getText().toString();
            // go to new activity (explicit intent) and pass in value of which sensor was clicked
//            Intent intent = new Intent(context, SensorDetailsActivity.class);
//            intent.putExtra("SENSOR_NAME", sensorName);
//            context.startActivity(intent);
        }
    }
}