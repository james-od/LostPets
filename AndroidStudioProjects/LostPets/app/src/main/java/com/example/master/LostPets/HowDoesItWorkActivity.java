package com.example.master.LostPets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//**************************************************************************************************
//HowDoesItWorkActivity Class
//**************************************************************************************************
public class HowDoesItWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_does_it_work);


    }
    public void ShowMap(View view) {

        Intent intent = new Intent(HowDoesItWorkActivity.this, MapPanePetActivity.class);
        startActivity(intent);
    }

}
