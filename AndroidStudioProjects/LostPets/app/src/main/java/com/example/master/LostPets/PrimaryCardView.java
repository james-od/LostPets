package com.example.master.LostPets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class PrimaryCardView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String LOG_TAG = "CardViewActivity";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //LeakCanary.install(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_card_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getApplicationContext(), getDataSet());
        if(getDataSet().size() < 1){
            setContentView(R.layout.activity_cards_failed_to_load_actvitiy);
        }
        mRecyclerView.setAdapter(mAdapter);

        Button button = (Button) findViewById(R.id.retryButton);
        if(button != null){
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog = new ProgressDialog(PrimaryCardView.this);
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog.setMessage("Loading. Please wait...");
                    dialog.setIndeterminate(true);
                    dialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(PrimaryCardView.this, DataService.class);
                            startService(intent);
                            finish();
                        }
                    }, 1000);
                }
            });
        }

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

        System.out.println("primary card view get data set call");
        //System.out.println(StoreValsClass.GPStra4.toString());
        ArrayList results = new ArrayList<GPSTestResultsArr>();
        System.out.println("storevalsclass size in primarycardview get data set " + StoreValsClass.GPStra4.size());
        for (int index = 0; index < StoreValsClass.GPStra4.size(); index++) {
            //System.out.println("GPStra4 at index = "+StoreValsClass.GPStra4.get(index));
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