package com.example.fridgeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.fridgeapp.R.layout.activity_edit_ingredients;
import static com.example.fridgeapp.R.layout.row;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    public ArrayList<String> list, ingredientId;


    private Context context;

    Activity activity;

    public MyAdapter(ArrayList<String> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
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
            holder.ingredientNameTextView.setText(splitArr[1]);
            holder.ingredientTypeTextView.setText(splitArr[2]);
            holder.ingredientQuantityTextView.setText("Qty: " + splitArr[3]);
            if (splitArr[3].equals("0")) {
                // if qty is zero, gray out the name and change the color of qty to 0
                holder.ingredientQuantityTextView.setTextColor(Color.argb(90, 200, 0, 0));
                holder.ingredientNameTextView.setTextColor(Color.argb(50,00,00,00));
            }

            holder.myLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditIngredients.class);


                    i.putExtra("id", String.valueOf(splitArr[0]));
                    i.putExtra("name", String.valueOf(splitArr[1]));
                    i.putExtra("type", String.valueOf(splitArr[2]));
                    i.putExtra("quantity", String.valueOf(splitArr[3]));

                    activity.startActivityForResult(i, 1);


                }
            });
          //  holder.ingredientQuantityTextView.setText("Qty: " + splitArr[2]);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public RecyclerView myRecycler;
//        public LinearLayout myLayout;
        public CardView myLayout;
        public TextView ingredientNameTextView, ingredientTypeTextView, ingredientQuantityTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

            myLayout = itemView.findViewById(R.id.listLayout);

            myRecycler = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            ingredientNameTextView = (TextView) itemView.findViewById(R.id.ingredientNameEntry);
            ingredientTypeTextView = (TextView) itemView.findViewById(R.id.ingredientTypeEntry);
            ingredientQuantityTextView = (TextView) itemView.findViewById(R.id.ingredientQuantityEntry);
        }
    }
}