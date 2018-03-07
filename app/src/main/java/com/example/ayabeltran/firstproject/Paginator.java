package com.example.ayabeltran.firstproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

/**
 * Created by ayabeltran on 20/02/2018.
 */

public class Paginator{

    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    private RecyclerAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    Cursor cursor;
    dbhelper mydb;
    SQLiteDatabase sqLiteDatabase;




    public Paginator(Context c, PullToLoadView pullToLoadView) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;

        //RECYCLERVIEW
        RecyclerView rv=pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL,false));

        adapter= new RecyclerAdapter(new ArrayList<Place>(), c);
        rv.setAdapter(adapter);

        initializePaginator();
    }

    /*
    PAGE DATA
     */
    public void initializePaginator()
    {
        mydb = new dbhelper(c);
        sqLiteDatabase = mydb.getReadableDatabase();
        cursor = mydb.itemslisted(sqLiteDatabase);

        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {

            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                loadData(nextPage);
            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll=false;
                loadData(1);
//                sqLiteDatabase.execSQL("select * from imgTable order by "+dbhelper.t2col1+" desc  limit 5 offset 5");
            }

            //IS LOADING
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });

        pullToLoadView.initLoad();
    }

    /*
     LOAD MORE DATA
     SIMULATE USING HANDLERS
     */
    public void loadData(final int page)
    {
        isLoading=true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                //ADD CURRENT PAGE'S DATA
                for (int i=0;i<=5;i++)
                {

//                    adapter.add(new Place("Place : "+String.valueOf(i)+" in Page : "+String.valueOf(page)));
//                    int id;
//                    String name, des;
//                    byte[] photo;
//
//                    id = cursor.getInt(cursor.getColumnIndex("id"));
//                    photo = cursor.getBlob(cursor.getColumnIndex("photo"));
//                    name = cursor.getString(cursor.getColumnIndex("name"));
//                    des = cursor.getString(cursor.getColumnIndex("des"));
//
//                    Place places = new Place(id, photo, name, des);
//                    adapter.getPlaces().add(places);
//
//                    cursor.moveToNext();

                    if (cursor.moveToFirst()) {
                        do {
                            int id;
                            String name, des;
                            byte[] photo;

                            id = cursor.getInt(cursor.getColumnIndex("id"));
                            photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                            name = cursor.getString(cursor.getColumnIndex("name"));
                            des = cursor.getString(cursor.getColumnIndex("des"));

                            Place places = new Place(id, photo, name, des);
                            adapter.getPlaces().add(places);
                        }
                        while (cursor.moveToNext());
                    }
                }

                //UPDATE PROPETIES
                pullToLoadView.setComplete();
                isLoading=false;
                nextPage=page+1;

            }
        },3000);
    }



}
