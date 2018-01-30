package com.example.ayabeltran.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UserLogin extends AppCompatActivity {

    dbhelper mydb;

    EditText etloginame, etloginpword;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mydb = new dbhelper(this);

        etloginame = findViewById(R.id.etLoginUname);
        etloginpword = findViewById(R.id.etLogoinPword);
        btn_login = findViewById(R.id.btn_Login);





    }
}
