package com.example.ayabeltran.firstproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class newImg extends AppCompatActivity {

    dbhelper mydb;

    EditText etnewimgname, etdesc;
    ImageView btnimg;
    Button btnaddimg;
    private static int SELECT_IMAGE = 1;
    private static int CAPTURE_IMAGE = 2 ;
    Uri selectedimage;
    Bitmap camImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_img);

        mydb = new dbhelper(this);

        etnewimgname = findViewById(R.id.etNewimgname);
        etdesc = findViewById(R.id.etDesc);
        btnimg = findViewById(R.id.btnImg);
        btnaddimg = findViewById(R.id.btnAddimg);


        btnimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder(newImg.this);
                        View mview = getLayoutInflater().inflate(R.layout.dialog_add_image, null);
                        Button mbtnUseCam = mview.findViewById(R.id.btnUseCam);
                        Button mbtnUseGal = mview.findViewById(R.id.btnUseGal);
                        mbuilder.setView(mview);
                        final AlertDialog dialog = mbuilder.create();

                        mbtnUseCam.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(i, CAPTURE_IMAGE);
                                dialog.dismiss();
                            }
                        });

                        mbtnUseGal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(newImg.this,
                                new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                SELECT_IMAGE);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                }
        );
        btnaddimg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddImage();
                    }
                }
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == SELECT_IMAGE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, SELECT_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedimage = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedimage);
                Bitmap yourselectedimage = BitmapFactory.decodeStream(inputStream);
                btnimg.setImageBitmap(yourselectedimage);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK && data != null) {
            camImg =(Bitmap) data.getExtras().get("data");
            try {
                btnimg.setImageBitmap(camImg);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void AddImage(){
        final String name = etnewimgname.getText().toString();
        final String des = etdesc.getText().toString();
        final byte [] data = getimagebyte(btnimg);



        if (name.isEmpty()){
            Toast.makeText(newImg.this, "please enter an image name.", Toast.LENGTH_SHORT).show();
            etnewimgname.requestFocus();
            return;
        }

        if(des.isEmpty()){
            Toast.makeText(newImg.this, "please describe image.", Toast.LENGTH_SHORT).show();
            etdesc.requestFocus();
            return;
        }


        mydb.addimg(data, name, des);
        Toast.makeText(newImg.this, "new photo added", Toast.LENGTH_SHORT).show();

        Intent uploaded = new Intent(newImg.this, List.class);
        startActivity(uploaded);

        etnewimgname.setText("");
        etdesc.setText("");
    }


    public static byte[] getimagebyte (ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte [] bytearray = stream.toByteArray();
        return bytearray;
    }

}