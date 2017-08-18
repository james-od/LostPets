package com.example.master.LostPets;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class locationActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Button btnGetLastLocation;
    TextView textLastLocation;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_location);
        btnGetLastLocation = (Button) findViewById(R.id.getlastlocation);
        btnGetLastLocation.setOnClickListener(btnGetLastLocationOnClickListener);
        textLastLocation = (TextView) findViewById(R.id.lastlocation);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    View.OnClickListener btnGetLastLocationOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(mGoogleApiClient != null){
                if(mGoogleApiClient.isConnected()){
                    getMyLocation();
                }else{
                    Toast.makeText(locationActivity.this,
                            "!mGoogleApiClient.isConnected()", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(locationActivity.this,
                        "mGoogleApiClient == null", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void getMyLocation(){
        try{
            /* code should explicitly check to see if permission is available
            (with 'checkPermission') or explicitly handle a potential 'SecurityException'
             */
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                textLastLocation.setText(
                        String.valueOf(mLastLocation.getLatitude()) + "\n"
                                + String.valueOf(mLastLocation.getLongitude()));
                Toast.makeText(locationActivity.this,
                        String.valueOf(mLastLocation.getLatitude()) + "\n"
                                + String.valueOf(mLastLocation.getLongitude()),
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(locationActivity.this,
                        "mLastLocation == null",
                        Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException e){
            Toast.makeText(locationActivity.this,
                    "SecurityException:\n" + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(locationActivity.this,
                "onConnectionSuspended: " + String.valueOf(i),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(locationActivity.this,
                "onConnectionFailed: \n" + connectionResult.toString(),
                Toast.LENGTH_LONG).show();
    }
}

