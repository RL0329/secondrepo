package com.example.ayabeltran.firstproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ayabeltran on 01/02/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Place> places = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(ArrayList<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }


    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.activity_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView des;
        ImageView photo;
        Place selectedPlace;

        public MyViewHolder(View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.displayimage);
            name = itemView.findViewById(R.id.textname);
            des = itemView.findViewById(R.id.textdetails);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent preview = new Intent(context, ListDisplay.class);
                    preview.putExtra("Key", selectedPlace.getName());
                    preview.putExtra("Key2", selectedPlace.getDes());
                    preview.putExtra("Key3", selectedPlace.getPhoto());
                    context.startActivity(preview);

                }
            });
        }
    }

    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {

        (holder).photo.setImageResource(places.get(position).getPhoto());
        (holder).name.setText(places.get(position).getName());
        (holder).des.setText(places.get(position).getDes());
        (holder).selectedPlace = places.get(position);
    }


    public int getItemCount() {
        return places.size();
    }
}

