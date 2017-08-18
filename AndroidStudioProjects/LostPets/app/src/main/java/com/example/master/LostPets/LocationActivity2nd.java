package com.example.master.LostPets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import android.widget.Button;
    import android.widget.Toast;

public class LocationActivity2nd extends AppCompatActivity {

        private Button b_get;
        private TrackGPS gps;
        double longitude;
        double latitude;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_location_activity2nd);
            b_get = (Button)findViewById(R.id.get);
            b_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gps = new TrackGPS(LocationActivity2nd.this);
                    if(gps.canGetLocation()){
                        longitude = gps.getLongitude();
                        latitude = gps .getLatitude();
                        Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        gps.showSettingsAlert();
                    }
                }
            });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            gps.stopUsingGPS();
        }
    }