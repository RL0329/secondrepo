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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    Cursor pulled;

    RecyclerAdapter recyclerAdapter;

    Boolean isScrolling=false;
    int currentItems=5,
            totalItems,
            scrollOutItems=5;
    ProgressBar progressBar;





    ////////////////////////////////////////////////////////////////////////////////////////////////
    public FragList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_frag_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);
        mswipeRefreshLayout = v.findViewById(R.id.swiperefresh);
         progressBar = v.findViewById(R.id.progress);


        // adapter
        recyclerAdapter = new RecyclerAdapter(places, getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        mydb = new dbhelper(getActivity());
        sqLiteDatabase = mydb.getReadableDatabase();
        cursor = mydb.itemslisted(sqLiteDatabase);






        EndlessScroll();

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


    private void EndlessScroll(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling=true;
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=mLayoutManager.getChildCount();
                totalItems=mLayoutManager.getItemCount();

                int rowCount = mydb.getimgTableCount();
                pulled = pulledItens(sqLiteDatabase);




                scrollOutItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (isScrolling && (currentItems+scrollOutItems < rowCount) && (places.size()<rowCount))
                {


                    fetchData();
                    isScrolling=false;
                }
                else {
                    isScrolling=false;
                }

            }


        });
    }

    public Cursor pulledItens (SQLiteDatabase db) {
        String pull = "select * from imgTable order by " + dbhelper.t2col1 + " desc limit 5 offset "+(totalItems);
        Log.d("pull", pull);
        Cursor cursor = db.rawQuery(pull, null);
        return cursor;
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


                Fragment frag= null;
                frag = getFragmentManager().getFragments().get(0);
                final android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.detach(frag);
                ft.attach(frag);
                ft.commit();


            }
        }, 3000);
    }
    private void fetchData(){

        if (pulled.moveToFirst()) {
            do {
                int id;
                String name, des;
                byte[] photo;

                id = pulled.getInt(pulled.getColumnIndex("id"));
                photo = pulled.getBlob(pulled.getColumnIndex("photo"));
                name = pulled.getString(pulled.getColumnIndex("name"));
                des = pulled.getString(pulled.getColumnIndex("des"));

                Place places = new Place(id, photo, name, des);
                recyclerAdapter.getPlaces().add(places);
            }
            while (pulled.moveToNext());


            String count = String.valueOf(totalItems);
            Toast.makeText(getActivity(),count, Toast.LENGTH_SHORT).show();
        }

        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                for(int i=0;i<3;i++) {


                    recyclerAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);



                }


            }
        }, 3000);
    }



}



