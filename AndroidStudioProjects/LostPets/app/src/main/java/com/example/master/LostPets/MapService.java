package com.example.master.LostPets;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import java.util.regex.*;


public class MapService extends FragmentActivity{

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public ArrayList<String[]> als = new ArrayList<>();

    public JSONObject response_var=null;

    private GoogleApiClient mLocationClient;
    private Location mCurrentLocation;
    LocationRequest mLocationRequest;

    private TrackGPS gps;
    double userLongitude;
    double userLatitude;
    public String closestPet1="INIT VALUE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("START");
        moveTaskToBack(true);
        Intent intent = new Intent(MapService.this, MyActivity.class);
        startActivity(intent);



        closestPet1="create VALUE";
        System.out.println("CREATED");
        super.onCreate(savedInstanceState);


        gps = new TrackGPS(MapService.this);


        if(gps.canGetLocation()) {
            userLongitude = gps.getLongitude();
            userLatitude = gps.getLatitude();
            //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(userLongitude) + "\nLatitude:" + Double.toString(userLatitude), Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(), "Can't get user location", Toast.LENGTH_SHORT).show();
        }



        //JSON to find co-ords
        //Store Co-ords in something
        System.out.println("DECLARING VARIABLES");
        int id = -1;
        String name = "";

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("TRYING JSON RESPONSE...");
                    JSONArray jObj = new JSONArray(response);
                    GPSTestResultsArr[] GPStra = new GPSTestResultsArr[jObj.length()];
                    System.out.println("jObj.length() = " + jObj.length());
                    System.out.println("jObj = " + jObj);

                    double closestPetDist=Double.POSITIVE_INFINITY;
                    String closestPet="";

                    for (int i = 0; i < jObj.length(); i++) {

                        JSONObject jobj = jObj.getJSONObject(i);
                        String name = jobj.getString("Name");
                        String gender = jobj.getString("Gender");
                        String regLost = jobj.getString("RegionLost");
                        String postCode = jobj.getString("PostCode");
                        String breed = jobj.getString("Breed");
                        double latitude = jobj.getDouble("Latitude");
                        double longitude = jobj.getDouble("Longitude");
                        long id = jobj.getLong("ID");
                        System.out.println("MapPanePetActivity : id = " + id);
                        String imageAddress = jobj.getString("imageAddress");
                        double distancefromUser = ( Math.pow(Math.pow(userLatitude-latitude,2)+Math.pow(userLongitude-longitude,2),0.5) );

                        if(distancefromUser<closestPetDist){
                            closestPetDist = distancefromUser;
                            closestPet = name;
                        }

                        //GPStra[i] = new GPSTestResultsArr(latitude,longitude,id,name,species,description); //1!2@3#4$
                        GPStra[i] = new GPSTestResultsArr(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);


                        //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);


                        StoreValsClass svc = new StoreValsClass();
                        StoreValsClass.addSet(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);

                    }
                    if(closestPet != null){
                        closestPet1=closestPet;
                    }
                    System.out.println("closestPet1: "+closestPet1+" closestPet "+closestPet);
                    //Toast.makeText(getApplicationContext(), "closestPet is "+closestPet1,Toast.LENGTH_SHORT).show();
                    String s = jObj.toString();
                    System.out.println("GPSTest TABLE VALUES" + s);

                    ArrayList<String> list = new ArrayList<String>();
                    JSONArray jsonArray = (JSONArray) jObj;
                    if (jsonArray != null) {
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            list.add(jsonArray.get(i).toString());
                            //System.out.println(list.get(i) + "\n");

                            Double[] doub = processString(list.get(i));

                            System.out.println("don't think this bit does anything any more");
                            /*
                            MarkerOptions marker = new MarkerOptions().position(
                                    new LatLng(doub[0], doub[1])).title("this new marker");
                            mMap.addMarker(new MarkerOptions().position(new LatLng(doub[0], doub[1])).title("Added Point").snippet("Worked yas").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_paw)));
                            //removed .position(point) from above after markerOptions

                               */
                        }
                    }


                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapService.this);
                    builder.setMessage("Failed to initialise pet locations")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                    System.out.println("CAUGHT EXCEPTION:");
                    e.printStackTrace();
                }
            }
        };

        String[] array = new String[0];
        GetLatLongsRequest getLLR = new GetLatLongsRequest(array, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapService.this);
        queue.add(getLLR);

        System.out.println("PARAMS: \n \n \n");
        System.out.println(getLLR.getParams());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        System.out.println("TIMER");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("END");
        Intent intent2 = new Intent(MapService.this, PrimaryCardView.class);
        startActivity(intent2);
        finish();
    }



    public static Double[] processString(String s) {
        //System.out.println("start string: "+s);
        double latitude = 0.0;
        double longitude = 0.0;

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        ArrayList<String> sArr = new ArrayList<>();

        while (m.find()) {
            //System.out.println(m.group());
            sArr.add(m.group());
        }

        //System.out.println(sArr.toString());
        //latitude = Double.parseDouble((sArr.get(0) + '.' + sArr.get(1)));
        //System.out.println("latitude = " + latitude);
        //longitude = Double.parseDouble((sArr.get(2) + '.' + sArr.get(3)));
        //System.out.println("longitude = " + longitude);
        //int id = Integer.parseInt(sArr.get(4));

        Double[] retArr = new Double[2];
        retArr[0] = latitude;
        retArr[1] = longitude;
        return retArr;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps = null;
    }

}


