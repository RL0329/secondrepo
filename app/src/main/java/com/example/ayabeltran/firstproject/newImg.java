package com.example.ayabeltran.firstproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    private static int selectImg = 1;
    private static int RESULT_IMAGE = 2;
    Uri selectedimage;

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
                        ActivityCompat.requestPermissions(newImg.this,
                                new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                RESULT_IMAGE);
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
        if(requestCode == RESULT_IMAGE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, RESULT_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMAGE && resultCode == RESULT_OK && data!=null){
            selectedimage = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(selectedimage);
                Bitmap yourselectedimage = BitmapFactory.decodeStream(inputStream);
                btnimg.setImageBitmap(yourselectedimage);

            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT).show();
            }
        }
    }
//
//    public  void SelectImage(){
////        Intent i =  new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
////
////        startActivityForResult(i, selectImg);
//        ActivityCompat.requestPermissions(newImg.this,
//                new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},
//                selectImg);
//
//    }
//
//    // lowers api compatibility and checks is package managers permission is granted
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == selectImg){
//            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//
//                //if granted intent goes to designated activity result
//                Intent i = new Intent(Intent.ACTION_PICK);
//                i.setType("image/");
//                startActivityForResult(i, selectImg);
//            }
//        }
//    }
//
//    @Override
//            protected void onActivityResult(int requestcode, int resultcode, Intent data){
//                super.onActivityResult(requestcode, resultcode, data);
//
//                if(requestcode == selectImg && resultcode == RESULT_OK && data != null){
//                    Uri imgUri = data.getData();
//                    btnimg.setImageURI(imgUri);
//                }
//            }

    public void AddImage(){
        // getting image from the image button
//        Bitmap selectedImg = ((BitmapDrawable) btnimg.getDrawable()).getBitmap();
//        selectedImg = Bitmap.createScaledBitmap(selectedImg, selectedImg.getWidth() / 2, selectedImg.getHeight() / 2, true);
        String name = etnewimgname.getText().toString();
        String des = etdesc.getText().toString();

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


        ////trying this////

//        File f = new File(getFilesDir().getAbsolutePath(), name + " " + des ".jpg");
//        FileOutputStream outputFile;
//
//        try {
//            outputFile = new FileOutputStream(f);
//            selectedImg.compress(Bitmap.CompressFormat.JPEG, 50, outputFile);
//
//        } catch (Exception e) {
//
//        }

        // allocating memory to store the image for byte conversion later
//        ByteBuffer bb = ByteBuffer.allocate(selectedImg.getByteCount());
//
//
//        // copying bitmap to allocated memory from ByteBuffer
//        selectedImg.copyPixelsToBuffer(bb);

        // using byte array to store photo (since parameter needed is a byte[] array)
        // byte[] photo = bb.array();
        byte [] data = getimagebyte(btnimg);

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