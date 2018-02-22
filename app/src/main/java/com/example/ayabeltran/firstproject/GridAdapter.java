package com.example.ayabeltran.firstproject;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.*;
import java.util.List;

/**
 * Created by ayabeltran on 01/02/2018.
 */


public class GridAdapter extends RecyclerView.Adapter <GridAdapter.MyViewHolder> {

    private ArrayList<Place> places = new ArrayList<>();
    private Context context;

    public GridAdapter(ArrayList<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }
    public ArrayList<Place> getPlaces() {
        return this.places;
    }


    @Override
    public GridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater gInflater = LayoutInflater.from(context);
        View view1 = gInflater.inflate(R.layout.activity_grid_view_layout, parent, false);
        GridAdapter.MyViewHolder holder = new GridAdapter.MyViewHolder(view1);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView des;
        ImageView photo;
        Place selectedPlace;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.photo = itemView.findViewById(R.id.imageViewGrid);
            this.name = itemView.findViewById(R.id.textViewGrid);
//            this.des = itemView.findViewById(R.id.textdetails);

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

    @Override
    public void onBindViewHolder(GridAdapter.MyViewHolder holder, int position) {

        //        getting the original photo from the list
        byte[] originalPhoto = places.get(position).getPhoto();

//        converting the photo bytes to usable image
        Bitmap decodedPhoto = BitmapFactory.decodeByteArray(originalPhoto, 0, originalPhoto.length);

        holder.photo.setImageBitmap(decodedPhoto);
        holder.name.setText(places.get(position).getName());
//        holder.des.setText(places.get(position).getDes());
        holder.selectedPlace = places.get(position);
        Glide.with(context).load(places.get(position).getPhoto()).into(holder.photo);
//        Toast.makeText(context, places.get(position).getPhoto().toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return places.size();
    }

//    public void clearData() {
//        places.clear();
//        notifyDataSetChanged();
//    }
}