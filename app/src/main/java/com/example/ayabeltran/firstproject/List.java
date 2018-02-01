package com.example.ayabeltran.firstproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    Button add;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Place> places = new ArrayList<>();

    dbhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        add = findViewById(R.id.btnadd);
        recyclerView = findViewById(R.id.recyclerview);

        mydb = new dbhelper(this);
        RecyclerAdapter adapter = new RecyclerAdapter(places,this);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toUpload = new Intent(List.this, newImg.class);
                startActivity(toUpload);
            }
        });


    }
}
