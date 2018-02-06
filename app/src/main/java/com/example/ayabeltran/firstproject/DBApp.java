package com.example.ayabeltran.firstproject;

import android.app.Application;

/**
 * Created by Lorenzo11 on 06/02/2018.
 */

public class DBApp extends Application{

    private dbhelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new dbhelper(this);

    }

    public dbhelper getDatabase(){
        return db;

    }
}
