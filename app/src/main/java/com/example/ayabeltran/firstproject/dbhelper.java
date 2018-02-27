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

        public static final String dbname = "task1_4th.db",
                                    loginTable = "loginDetails",
                                    imgTable = "imgTable",
                                    Tname3 ="newImgTable",
                                    loginID ="id",
                                    loginEmail ="email",
                                    loginUname ="uname",
                                    loginPword ="pword",
                                    loginFname ="fname",
                                    loginLname ="lname",
                                    imgID ="id",
                                    imgPhoto ="photo",
                                    imgName ="name",
                                    imgDesc ="des",
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
        db.execSQL("create table "+ loginTable +" ( "
                + loginID +" integer primary key autoincrement, "
                + loginEmail +" text not null unique, "
                + loginUname +" text not null unique, "
                + loginPword +" text not null, "
                + loginFname +" text not null, "
                + loginLname +" text not null);");

        db.execSQL("create table "+ imgTable +" ("
                + imgID +" integer primary key autoincrement, "
                + imgPhoto +" blob not null, "
                + imgName +" text not null, "
                + imgDesc +" text not null);");

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
        db.execSQL("drop table if exists "+ loginTable);
        db.execSQL("drop table if exists " + imgTable);
        onCreate(db);
    }
        // method that inserts values into the userdetails table //
    public boolean adduser(String email, String uname, String pword, String fname, String lname) {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(loginEmail, email);
        contentValues.put(loginUname, uname);
        contentValues.put(loginPword, pword);
        contentValues.put(loginFname, fname);
        contentValues.put(loginLname, lname);
        long result = db.insert(loginTable, null, contentValues);
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
         long result = db.insert(imgTable, null, contentValues);
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
        String items = "select * from imgTable order by "+ imgID +" desc limit 10";
        Log.d("items", items);
        Cursor cursor = db.rawQuery(items, null);
        return cursor;
    }

    public Cursor gridItemslisted (SQLiteDatabase db) {
        String items = "select * from imgTable order by "+ imgID +" desc limit 15";
        Log.d("items", items);
        Cursor cursor = db.rawQuery(items, null);
        return cursor;
    }

        // total entries in imgTable
      public int getimgTableCount() {
        String countQuery = "SELECT  * FROM imgTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}
