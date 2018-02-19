package com.example.ayabeltran.firstproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewLayout extends AppCompatActivity {

    ImageView image;
    TextView name;
//            description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_layout);


            image = findViewById(R.id.imageViewGrid);
            name = findViewById(R.id.textViewGrid);
//            description = findViewById(R.id.textdetails);

            Bundle extra = getIntent().getExtras();
            String  Key = extra.getString("Key");
            String  Key2 = extra.getString("Key2");
            byte[]  Key3 = extra.getByteArray("Key3");

            name.setText(Key);
//            description.setText(Key2);
            Bitmap bm = BitmapFactory.decodeByteArray(Key3, 0, Key3.length);
            image.setImageBitmap(bm);
        }
}
