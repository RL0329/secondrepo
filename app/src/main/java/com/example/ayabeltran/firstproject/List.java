package com.example.ayabeltran.firstproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class List extends AppCompatActivity {

    Button add;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        add = findViewById(R.id.btnadd);
        recyclerView = findViewById(R.id.recyclerview);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toUpload = new Intent(List.this, ListDisplay.class);
                startActivity(toUpload);
            }
        });


    }
}
