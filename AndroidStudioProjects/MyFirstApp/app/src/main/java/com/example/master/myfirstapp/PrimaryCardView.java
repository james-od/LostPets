package com.example.master.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PrimaryCardView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LeakCanary.install(getApplication());
        //Toast.makeText(getApplicationContext(), "Create", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_card_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getApplicationContext(), getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrimaryCardView.this, MyActivity.class);
                startActivity(intent);
                System.out.println("add paw was clicked");

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<GPSTestResultsArr> getDataSet() {

        System.out.println("\n\n\n\n\n1345");
        System.out.println(StoreValsClass.GPStra4.toString());
        ArrayList results = new ArrayList<GPSTestResultsArr>();
        for (int index = 0; index < StoreValsClass.GPStra4.size(); index++) {
            System.out.println("GPStra4 at index = "+StoreValsClass.GPStra4.get(index));
            GPSTestResultsArr obj = StoreValsClass.GPStra4.get(index);
            //GPSTestResultsArr obj = new GPSTestResultsArr("name","gender","regLost","postcode","breed",1.0,1.0,1,"testAddress");

            results.add(index, obj);
        }
        return results;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRecyclerView!=null)
        {
            System.out.println("Clearing mRecyclerView ");
            mRecyclerView.destroyDrawingCache();
            mRecyclerView=null;
            System.out.println("Cleared mRecyclerView ");
        }
        if(StoreValsClass.GPStra4!=null){
            System.out.println("Clearing GPStra4 " + StoreValsClass.GPStra4.size());
            StoreValsClass.GPStra4.clear();
            System.out.println("Cleared GPStra4 " + StoreValsClass.GPStra4.size());
        }
        if(mAdapter!=null){
            System.out.println("Clearing mAdapter ");
            mAdapter = null;
            System.out.println("Cleared mAdapter ");
        }
        if(mLayoutManager!=null){
            System.out.println("Clearing mLayoutManager ");
            mLayoutManager = null;
            System.out.println("Cleared mLayoutManager ");
        }
    }
}