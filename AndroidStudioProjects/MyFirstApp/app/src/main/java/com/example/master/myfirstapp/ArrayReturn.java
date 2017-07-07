package com.example.master.myfirstapp;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Master on 08-Oct-16.
 */
public class ArrayReturn extends AppCompatActivity {

    public ArrayReturn() {
    }
    public ArrayList<GPSTestResultsArr> returnArr(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<GPSTestResultsArr> GPStra= new ArrayList<GPSTestResultsArr>();
                try {
                    System.out.println("TRYING JSON RESPONSE...");
                    JSONArray jObj = new JSONArray(response);
                    System.out.println("jObj.length() = " + jObj.length());
                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jobj = jObj.getJSONObject(i);
                        String name = jobj.getString("name");
                        System.out.println("ArrayReturn: name = "+name);
                        String gender = jobj.getString("gender");
                        System.out.println("ArrayReturn: gender = "+gender);
                        String regLost = jobj.getString("regionLost");
                        System.out.println("ArrayReturn: regionLost = "+regLost);
                        String postCode = jobj.getString("PostCode");
                        System.out.println("ArrayReturn: PostCode = "+postCode);
                        String breed = jobj.getString("Breed");
                        System.out.println("ArrayReturn: Breed = "+breed);
                        double latitude = jobj.getDouble("latitude");
                        System.out.println("ArrayReturn: latitude = "+latitude);
                        double longitude = jobj.getDouble("longitude");
                        System.out.println("ArrayReturn: longitude = "+longitude);
                        long id = jobj.getLong("ID");
                        System.out.println("MapPanePetActivity : id = "+id);
                        String imageAddress = jobj.getString("imageAddress");
                        System.out.println("MapPanePetActivity : imageAddress = "+imageAddress);

                        GPStra.add(new GPSTestResultsArr(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress));

                    }
                    String s = jObj.toString();
                    System.out.println("GPSTest TABLE VALUES" + s);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String[] array = new String[0];
        GetLatLongsRequest getLLR = new GetLatLongsRequest(array, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ArrayReturn.this);
        queue.add(getLLR);

//doesn't do anything
        return null;
    }
}
