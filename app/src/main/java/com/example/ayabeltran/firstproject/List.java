package com.example.ayabeltran.firstproject;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class List extends AppCompatActivity {

    //widgets
    Button Add;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mswipeRefreshLayout;


    ArrayList<Place> places = new ArrayList();
    dbhelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Add = findViewById(R.id.btnadd);
        recyclerView = findViewById(R.id.recyclerview);
        mswipeRefreshLayout = findViewById(R.id.swiperefresh);


        // adapter
        recyclerAdapter = new RecyclerAdapter(places, this);
        recyclerView.setAdapter(recyclerAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);


        mydb = new dbhelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        cursor = mydb.itemslisted(sqLiteDatabase);


        mswipeRefreshLayout.setRefreshing(false);

        Log.d("Rows", cursor.getCount() + "");


        RefreshItems2();
//        Toast.makeText(this, "refresh 2", Toast.LENGTH_LONG).show();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {

                places.clear();

                RefreshItems();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerAdapter.notifyDataSetChanged();

                        // cancel the Visual indication of a refresh
                        mswipeRefreshLayout.setRefreshing(false);

                    }
                }, 3000);




            }
        });






        Add.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent toUpload = new Intent(List.this, newImg.class);
                startActivity(toUpload);
            }
        });
    }

      private void RefreshItems() {

          if (cursor.moveToFirst()) {
              do {
                  int id;
                  String name, des;
                  byte[] photo;

                  id = cursor.getInt(cursor.getColumnIndex("id"));
                  photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                  name = cursor.getString(cursor.getColumnIndex("name"));
                  des = cursor.getString(cursor.getColumnIndex("des"));

                  Place places = new Place(id, photo, name, des);
                  recyclerAdapter.getPlaces().add(places);


              }
              while (cursor.moveToNext());

          }

       }


       private void RefreshItems2() {

        if (cursor.moveToFirst()) {
            do {
                if(cursor.isFirst()){
                    //do nothing
                }
                else{
                    int id;
                    String name, des;
                    byte[] photo;

                    id = cursor.getInt(cursor.getColumnIndex("id"));
                    photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    des = cursor.getString(cursor.getColumnIndex("des"));

                    Place places = new Place(id, photo, name, des);
                    recyclerAdapter.getPlaces().add(places);
                }


            }
            while (cursor.moveToNext());

        }

    }


}