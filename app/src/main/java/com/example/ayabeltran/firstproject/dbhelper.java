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

        public static final String dbname = "task1_4th.db";
        public static final String Tname = "loginDetails";
        public static final String Tname2 = "imgTable",
                                    Tname3 ="newImgTable";

        public static final String col1 ="id";
        public static final String col2 ="email";
        public static final String col3 ="uname";
        public static final String col4 ="pword";
        public static final String col5 ="fname";
        public static final String col6 ="lname";
        public static final String t2col1 ="id",
                                    t2col2="photo",
                                    t2col3="name",
                                    t2col4="des",
                                    t3col1 ="id",
                                    t3col2="photo",
                                    t3col3="name",
                                    t3col4="des";





    public dbhelper(Context context) {
        //creates the database//
        super(context, dbname, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creates the tables //
        db.execSQL("create table "+Tname+" ( "
                +col1+" integer primary key autoincrement, "
                +col2+" text not null unique, "
                +col3+" text not null unique, "
                +col4+" text not null, "
                +col5+" text not null, "
                +col6+" text not null);");

        db.execSQL("create table "+Tname2+" ("
                +t2col1+" integer primary key autoincrement, "
                +t2col2+" blob not null, "
                +t2col3+" text not null, "
                +t2col4+" text not null);");

        db.execSQL("create table "+Tname3+" ("
                +t3col1+" integer primary key autoincrement, "
                +t3col2+" blob not null, "
                +t3col3+" text not null, "
                +t3col4+" text not null);");

        // inserts a default user into the db //
        db.execSQL("insert into loginDetails  (email, uname, pword, fname, lname) values " +
                "('test@test.com', 'test', '111', 'test', 'test')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Tname);
        db.execSQL("drop table if exists " + Tname2);
        onCreate(db);
    }
        // method that inserts values into the userdetails table //
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
        // method that inserts values into the newImgtable table //
    public boolean addimg(byte[] photo, String name, String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(t3col2, photo);
        contentValues.put(t3col3, name);
        contentValues.put(t3col4, des);
         long result = db.insert(Tname3, null, contentValues);
         if(result == -1)
             return false;
        else
            return true;
    }
        // method that looks for matching usernames and passwords //
    public Cursor userlogin(String loginame, String loginpword, SQLiteDatabase db){
        String query = "select * from loginDetails where uname = '"+loginame+"' and pword = '"+loginpword+"'";
        Log.d("query", query);
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
        // method that calls all the contents of the imgtable //
    public Cursor itemslisted (SQLiteDatabase db) {
        String items = "select * from imgTable order by "+t2col1+" desc limit 5";
        Log.d("items", items);
        Cursor cursor = db.rawQuery(items, null);
        return cursor;
    }

}
