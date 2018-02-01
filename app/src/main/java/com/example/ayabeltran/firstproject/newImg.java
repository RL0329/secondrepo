package com.example.ayabeltran.firstproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.nio.ByteBuffer;

public class newImg extends AppCompatActivity {

    dbhelper mydb;

    EditText etnewimgname, etdesc;
    ImageButton btnimg;
    Button btnaddimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_img);

        mydb = new dbhelper(this);

        etnewimgname = findViewById(R.id.etNewimgname);
        etdesc = findViewById(R.id.etDesc);
        btnimg = findViewById(R.id.btnImg);
        btnaddimg = findViewById(R.id.btnAddimg);


        SelectImage();
        AddImage();
    }

    public  void SelectImage(){

        btnimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

    }

    public void AddImage(){
        btnaddimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getting image from the image button
                        Bitmap selectedImg = ((BitmapDrawable) btnimg.getDrawable()).getBitmap();

                        // allocating memory to store the image for byte conversion later
                        ByteBuffer bb = ByteBuffer.allocate(selectedImg.getByteCount());

                        // copying bitmap to allocated memory from ByteBuffer
                        selectedImg.copyPixelsToBuffer(bb);

                        // using byte array to store photo (since parameter needed is a byte[] array)
                        byte[] photo = bb.array();

                        mydb.addimg(photo,
                                etnewimgname.getText().toString(),
                                etdesc.getText().toString()
                        );
                    }
                }
        );
    }
}
