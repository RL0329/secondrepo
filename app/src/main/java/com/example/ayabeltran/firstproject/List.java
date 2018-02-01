package com.example.ayabeltran.firstproject;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class List extends AppCompatActivity {

    Button Add;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Place> places = new ArrayList<>();
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



        // adapter
        RecyclerAdapter adapter = new RecyclerAdapter(places, this);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);


        mydb = new dbhelper(this);
        sqLiteDatabase = mydb.getReadableDatabase();
        cursor = mydb.itemslisted(sqLiteDatabase);


        if(cursor.moveToFirst()){
            do {
                int id;
                String name, des;
                byte [] photo;

                id = cursor.getInt(0);
                photo = cursor.getBlob(1);
                name = cursor.getString(2);
                des = cursor.getString(3);



                Place places = new Place(id, photo, name, des);
                this.places.add(places);
                recyclerAdapter.notifyDataSetChanged();
            }
            while (cursor.moveToNext());
        }



        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toUpload = new Intent(List.this, newImg.class);
                startActivity(toUpload);
            }
        });
    }



}




