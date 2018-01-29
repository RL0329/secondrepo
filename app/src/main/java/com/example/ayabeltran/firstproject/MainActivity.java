package com.example.ayabeltran.firstproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    dbhelper mydb;

    EditText etemail, etuname, etpword, etfname, etlname;
    Button btnreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new dbhelper(this);

        etemail = (EditText) findViewById(R.id.etEmail);
        etuname = (EditText) findViewById(R.id.etUname);
        etpword = (EditText) findViewById(R.id.etPword);
        etfname = (EditText) findViewById(R.id.etFname);
        etlname = (EditText) findViewById(R.id.etFname);
        btnreg1 = (Button) findViewById(R.id.btnReg1);
        btnreg2 = (Button) findViewById(R.id.btnReg2);

        Register();
    }

    public void Register() {
        btnreg2.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isInserted =  mydb.insertData(etemail.getText().toString(),
                        etuname.getText().toString(),
                        etpword.getText().toString(),
                        etfname.getText().toString(),
                        etlname.getText().toString());
               if (isInserted = true)
                   Toast.makeText(MainActivity.this, "you are now registered.", Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this, "your email or username is already in use.", Toast.LENGTH_LONG).show();

            }
        });
    }
}
