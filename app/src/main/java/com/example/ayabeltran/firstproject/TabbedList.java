package com.example.ayabeltran.firstproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TabbedList extends AppCompatActivity {

    //widgets
    Button Add;
//    RecyclerView recyclerView;
//    private RecyclerView.LayoutManager mLayoutManager;
//    SwipeRefreshLayout mswipeRefreshLayout;
//
//    ArrayList<Place> places = new ArrayList();
//    dbhelper mydb;
//    SQLiteDatabase sqLiteDatabase;
//    Cursor cursor;
//    RecyclerAdapter recyclerAdapter;


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Add = findViewById(R.id.btnadd);
//        recyclerView = findViewById(R.id.recyclerview);
//        mswipeRefreshLayout = findViewById(R.id.swiperefresh);
//
//
//
//        // adapter
//        recyclerAdapter = new RecyclerAdapter(places, this);
//        recyclerView.setAdapter(recyclerAdapter);
//        mLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(mLayoutManager);
//
//
//        mydb = new dbhelper(this);
//        sqLiteDatabase = mydb.getReadableDatabase();
//        cursor = mydb.itemslisted(sqLiteDatabase);
//
//
//        mswipeRefreshLayout.setRefreshing(false);
//
//        Log.d("Rows", cursor.getCount() + "");
//
//
//        onLoad();
//
//
//
//        mswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh(){
//
//                reload();
//
//
//            }
//        });
//
//
        Add.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent toUpload = new Intent(TabbedList.this, newImg.class);
                startActivity(toUpload);
            }
        });
//    }
//
//
//    private void onLoad() {
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id;
//                String name, des;
//                byte[] photo;
//
//                id = cursor.getInt(cursor.getColumnIndex("id"));
//                photo = cursor.getBlob(cursor.getColumnIndex("photo"));
//                name = cursor.getString(cursor.getColumnIndex("name"));
//                des = cursor.getString(cursor.getColumnIndex("des"));
//
//                Place places = new Place(id, photo, name, des);
//                recyclerAdapter.getPlaces().add(places);
//            }
//            while (cursor.moveToNext());
//        }
//    }
//    private void reload(){
//        sqLiteDatabase.execSQL("insert into "+dbhelper.Tname2+"("+dbhelper.t2col2+","+dbhelper.t2col3+","+dbhelper.t2col4+
//                ") select "+dbhelper.t3col2+","+dbhelper.t3col3+","+dbhelper.t3col4+" from "+dbhelper.Tname3);
//
//        sqLiteDatabase.execSQL("delete from "+dbhelper.Tname3);
//
//
//        places.clear();
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerAdapter.notifyDataSetChanged();
//
//                // cancel the Visual indication of a refresh
//                mswipeRefreshLayout.setRefreshing(false);
//                finish();
//                startActivity(getIntent());
//
//            }
//        }, 3000);
//    }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_list, menu);
        return true;
    }



    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_list, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
