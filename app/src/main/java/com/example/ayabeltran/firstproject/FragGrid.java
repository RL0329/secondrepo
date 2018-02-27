package com.example.ayabeltran.firstproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class FragGrid extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mswipeRefreshLayout;
    ArrayList<Place> places = new ArrayList();
    dbhelper mydb;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    GridAdapter gridAdapter;
    Boolean isScrolling=false;
    int totalItems;
    ProgressBar progressBar,
            progressBar2;
    Cursor pulled;

    public FragGrid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_grid, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);
        mswipeRefreshLayout = v.findViewById(R.id.swiperefresh);
        progressBar = v.findViewById(R.id.progress);

        // adapter
        gridAdapter = new GridAdapter(places, getActivity());
        recyclerView.setAdapter(gridAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        mydb = new dbhelper(getActivity());
        sqLiteDatabase = mydb.getReadableDatabase();
        cursor = mydb.gridItemslisted(sqLiteDatabase);

        refreshList();
        EndlessScroll();
        mswipeRefreshLayout.setRefreshing(false);

        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                places.clear();
                refreshList();

                if(mswipeRefreshLayout.isRefreshing())

                    progressBar2.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++) {

                            gridAdapter.notifyDataSetChanged();
                            progressBar2.setVisibility(View.GONE);
                            mswipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }, 3000);
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
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItems = mLayoutManager.getItemCount();
                pulled = pulledItens(sqLiteDatabase);
                int rowCount = mydb.getimgTableCount();
                int listSize = places.size();
                int itemsOnScreen = mLayoutManager.getChildCount();
                int lastVisItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();


                System.out.println("list size: "+listSize);
                System.out.println("last visible item: "+lastVisItem);
                System.out.println("items on screen: "+itemsOnScreen);

                if (isScrolling && (lastVisItem + 2) > listSize && (listSize < rowCount)) {

                    fetchData();
                }

            }
        });
    }

    public Cursor pulledItens(SQLiteDatabase db) {
                String pull = "select * from imgTable order by " + dbhelper.imgID + " desc limit 5 offset " + totalItems;
                Log.d("pull", pull);
                Cursor cursor = db.rawQuery(pull, null);
                return cursor;
            }

    private ArrayList<Place> convertCursorToListPlace(Cursor cursor) {
                ArrayList<Place> places = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        int id;
                        String name, des;
                        byte[] photo;

                        id = cursor.getInt(cursor.getColumnIndex("id"));
                        photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        des = cursor.getString(cursor.getColumnIndex("des"));

                        Place place = new Place(id, photo, name, des);
                        places.add(place);
                    }
                    while (cursor.moveToNext());

                }
                return places;
            }
    private void refreshList(){
                Cursor cursor = getItemsFromDB();
                populateList(cursor);

                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {

//                    gridAdapter.clearData();
                    gridAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }


            }
        }, 3000);
            }

    private void populateList(Cursor cursor) {
                places = convertCursorToListPlace(cursor);

                gridAdapter = new GridAdapter(places, getContext());

                recyclerView.setAdapter(gridAdapter);
            }

    private Cursor getItemsFromDB(){
                return mydb.itemslisted(sqLiteDatabase);

            }

            private void fetchData() {

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
                        gridAdapter.getPlaces().add(places);
                    }
                    while (pulled.moveToNext());
                }

                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++) {

                            gridAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                }, 3000);
            }
        }

