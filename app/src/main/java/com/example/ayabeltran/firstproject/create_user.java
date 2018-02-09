package com.example.ayabeltran.firstproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class create_user extends AppCompatActivity {

    dbhelper mydb;
    Button btnreg2;
    EditText etemail,
             etuname,
             etpword,
             etcpword,
             etfname,
             etlname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        mydb = new dbhelper(this);

        etemail = findViewById(R.id.etEmail);
        etuname = findViewById(R.id.etUname);
        etpword = findViewById(R.id.etPword);
        etcpword = findViewById(R.id.etCpword);
        etfname = findViewById(R.id.etFname);
        etlname = findViewById(R.id.etLname);
        btnreg2 = findViewById(R.id.btnReg2);

        Register();
    }

    public void Register() {
        btnreg2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddUser();
                    }
                });
    }
    public void AddUser(){
        String Nemail = etemail.getText().toString(),
                Nuname = etuname.getText().toString(),
                Npword = etpword.getText().toString(),
                Ncpword = etcpword.getText().toString(),
                Nfname = etfname.getText().toString(),
                Nlanme = etlname.getText().toString();


        if(Nemail.isEmpty() || !Nemail.contains("@")) {
            Toast.makeText(this, "Please enter an Email address.", Toast.LENGTH_SHORT).show();
            etemail.requestFocus();
            etemail.setText("");
            return;

        }
        if(Nuname.isEmpty()){
            Toast.makeText(this, "Please enter a username.", Toast.LENGTH_SHORT).show();
            etuname.requestFocus();
            etuname.setText("");
            return;
        }
        if(Npword.isEmpty()){
            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
            etpword.requestFocus();
            etpword.setText("");
            return;
        }
        if(!Ncpword.equals(Npword)){
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            etcpword.requestFocus();
            etcpword.setText("");
            return;
        }
        if(Nfname.isEmpty()){
            Toast.makeText(this, "Please enter your 1st name.", Toast.LENGTH_SHORT).show();
            etfname.requestFocus();
            etfname.setText("");
            return;
        }
        if(Nlanme.isEmpty()){
            Toast.makeText(this, "Please enter your surname.", Toast.LENGTH_SHORT).show();
            etlname.requestFocus();
            etlname.setText("");
            return;
        }

        boolean isInserted = mydb.adduser(Nemail, Nuname, Npword, Nfname,Nlanme);

        if (isInserted) {

            Toast.makeText(create_user.this, "you are now registered.", Toast.LENGTH_LONG).show();

            Intent toLogin = new Intent(create_user.this, UserLogin.class);
            startActivity(toLogin);
        }
        else {
        Toast.makeText(create_user.this, "your email or username is already in use.", Toast.LENGTH_LONG).show();
            etemail.setText("");
            etuname.setText("");
            etpword.setText("");
            etcpword.setText("");
            etfname.setText("");
            etlname.setText("");
        }
    }
}
