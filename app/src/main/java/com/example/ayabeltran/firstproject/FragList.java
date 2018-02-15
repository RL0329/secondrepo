package com.example.ayabeltran.firstproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class FragList extends Fragment {

    private OnFragmentInteractionListener mListener;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mswipeRefreshLayout;

    ArrayList<Place> places = new ArrayList();
    dbhelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    RecyclerAdapter recyclerAdapter;




    ////////////////////////////////////////////////////////////////////////////////////////////////
    public FragList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);
        mswipeRefreshLayout = v.findViewById(R.id.swiperefresh);

        // adapter
        recyclerAdapter = new RecyclerAdapter(places, getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        mydb = new dbhelper(getActivity());
        sqLiteDatabase = mydb.getReadableDatabase();
        cursor = mydb.itemslisted(sqLiteDatabase);

        mswipeRefreshLayout.setRefreshing(false);

        onLoad();

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){

                reload();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void onLoad() {

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
                recyclerAdapter.getPlaces().add(places);
            }
            while (cursor.moveToNext());
        }
    }
    private void reload(){
        sqLiteDatabase.execSQL("insert into "+dbhelper.Tname2+"("+dbhelper.t2col2+","+dbhelper.t2col3+","+dbhelper.t2col4+
                ") select "+dbhelper.t3col2+","+dbhelper.t3col3+","+dbhelper.t3col4+" from "+dbhelper.Tname3);

        sqLiteDatabase.execSQL("delete from "+dbhelper.Tname3);


        places.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerAdapter.notifyDataSetChanged();

                // cancel the Visual indication of a refresh
                mswipeRefreshLayout.setRefreshing(false);
                getActivity().finish();
                startActivity(getActivity().getIntent());

            }
        }, 3000);
    }
}
