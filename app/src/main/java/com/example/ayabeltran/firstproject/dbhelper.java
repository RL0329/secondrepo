package com.example.ayabeltran.firstproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Lorenzo11 on 29/01/2018.
 */

public class dbhelper extends SQLiteOpenHelper{

        public static final String dbname = "task1_3rd.db";
        public static final String Tname = "loginDetails";
        public static final String Tname2 = "imgTable";
        public static final String col1 ="id";
        public static final String col2 ="email";
        public static final String col3 ="uname";
        public static final String col4 ="pword";
        public static final String col5 ="fname";
        public static final String col6 ="lname";
        String t2col1 ="id",
                t2col2="photo",
                t2col3="name",
                t2col4="des";


    public dbhelper(Context context) {
        super(context, dbname, null, 3);
        Log.d("database", "db created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("database", "table created");

        db.execSQL("create table "+Tname+" ( "+col1+" integer primary key autoincrement, "+col2+" text not null unique, "+col3+" text not null unique, "+col4+" text not null, "+col5+" text not null, "+col6+" text not null);");
        db.execSQL("create table "+Tname2+" ("+t2col1+" integer primary key autoincrement, "+t2col2+" blob not null, "+t2col3+" text not null, "+t2col4+" text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Tname);
        db.execSQL("drop table if exists " + Tname2);
        onCreate(db);
    }

    public boolean adduser(String email, String uname, String pword, String fname, String lname) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, email);
        contentValues.put(col3, uname);
        contentValues.put(col4, pword);
        contentValues.put(col5, fname);
        contentValues.put(col6, lname);
        long result = db.insert(Tname, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean addimg(byte[] photo, String name, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(t2col2, photo);
        contentValues.put(t2col3, name);
        contentValues.put(t2col4, des);
         long result = db.insert(Tname2, null, contentValues);
         if(result == -1)
             return false;
        else
            return true;
    }

    public Cursor userlogin(String loginame, String loginpword, SQLiteDatabase db){
        String query = "select * from loginDetails where uname = '"+loginame+"' and pword = '"+loginpword+"'";
        Log.d("query", query);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }


}
