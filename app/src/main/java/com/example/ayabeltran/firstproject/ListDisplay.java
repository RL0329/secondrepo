package com.example.ayabeltran.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class ListDisplay extends AppCompatActivity {

    ImageButton image;
    EditText name;
    EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display);

        image = findViewById(R.id.displayimage);
        name = findViewById(R.id.editname);
        description = findViewById(R.id.editdetails);




    }
}
