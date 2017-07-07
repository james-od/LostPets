package com.example.master.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

public class HowDoesItWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_does_it_work);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HowDoesItWorkActivity.this, MyActivity.class);
                startActivity(intent);
                System.out.println("add paw was clicked");

            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void ShowMap(View view) {

        Intent intent = new Intent(HowDoesItWorkActivity.this, MapPanePetActivity.class);
        startActivity(intent);
    }

}
