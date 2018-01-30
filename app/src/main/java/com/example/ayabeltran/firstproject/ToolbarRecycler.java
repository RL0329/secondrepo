package com.example.ayabeltran.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class ToolbarRecycler extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_recycler);

        toolbar = findViewById(R.id.uploadtoolbar);
        recyclerView = findViewById(R.id.recyclerview);

        //Call database




    }
}
