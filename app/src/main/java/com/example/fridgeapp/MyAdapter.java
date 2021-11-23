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
        String splitArr[] = res.split(","); //split string along commas

        //check that string was split into 3 or more parts
        if (splitArr.length > 2) {
            //display data into separate TextViews
            holder.ingredientNameTextView.setText(splitArr[0]);
            holder.ingredientTypeTextView.setText(splitArr[1]);
            holder.ingredientQuantityTextView.setText("Qty: " + splitArr[2]);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RecyclerView myRecycler;
        public LinearLayout myLayout;
        public TextView ingredientNameTextView, ingredientTypeTextView, ingredientQuantityTextView;
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            myRecycler = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            ingredientNameTextView = (TextView) itemView.findViewById(R.id.ingredientNameEntry);
            ingredientTypeTextView = (TextView) itemView.findViewById(R.id.ingredientTypeEntry);
            ingredientQuantityTextView = (TextView) itemView.findViewById(R.id.ingredientQuantityEntry);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {

        }
    }
}