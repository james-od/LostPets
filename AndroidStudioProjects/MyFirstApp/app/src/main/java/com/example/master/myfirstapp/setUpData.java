package com.example.master.myfirstapp;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Master on 31-May-17.
 */

public class setUpData {


    public static class setUpData1{

        double userLongitude;
        double userLatitude;
        public String closestPet1 = "INIT VALUE";


        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("TRYING JSON RESPONSE...");
                    JSONArray jObj = new JSONArray(response);
                    GPSTestResultsArr[] GPStra = new GPSTestResultsArr[jObj.length()];
                    System.out.println("jObj.length() = " + jObj.length());
                    System.out.println("jObj = " + jObj);

                    double closestPetDist = Double.POSITIVE_INFINITY;
                    String closestPet = "";

                    for (int i = 0; i < jObj.length(); i++) {

                        JSONObject jobj = jObj.getJSONObject(i);
                        String name = jobj.getString("Name");
                        System.out.println("MapPanePetActivity : name = " + name);
                        String gender = jobj.getString("Gender");
                        System.out.println("MapPanePetActivity : Gender = " + gender);
                        String regLost = jobj.getString("RegionLost");
                        System.out.println("MapPanePetActivity : RegionLost = " + regLost);
                        String postCode = jobj.getString("PostCode");
                        System.out.println("MapPanePetActivity : Postcode = " + postCode);
                        String breed = jobj.getString("Breed");
                        System.out.println("MapPanePetActivity : Breed = " + breed);
                        double latitude = jobj.getDouble("Latitude");
                        System.out.println("MapPanePetActivity : latitude = " + latitude);
                        double longitude = jobj.getDouble("Longitude");
                        System.out.println("MapPanePetActivity : longitude = " + longitude);
                        long id = jobj.getLong("ID");
                        System.out.println("MapPanePetActivity : id = " + id);
                        String imageAddress = jobj.getString("imageAddress");
                        System.out.println("MapPanePetActivity : imageAddress = " + imageAddress);
                        double distancefromUser = (Math.pow(Math.pow(userLatitude - latitude, 2) + Math.pow(userLongitude - longitude, 2), 0.5));
                        System.out.println("Distance from user " + distancefromUser);

                        if (distancefromUser < closestPetDist) {
                            closestPetDist = distancefromUser;
                            closestPet = name;
                        }

                        //GPStra[i] = new GPSTestResultsArr(latitude,longitude,id,name,species,description); //1!2@3#4$
                        GPStra[i] = new GPSTestResultsArr(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);


                        //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);


                        StoreValsClass svc = new StoreValsClass();
                        StoreValsClass.addSet(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);
                    }
                    if (closestPet != null) {
                        closestPet1 = closestPet;
                    }
                    System.out.println("closestPet1: " + closestPet1 + " closestPet " + closestPet);
                    String s = jObj.toString();
                    System.out.println("GPSTest TABLE VALUES" + s);

                    ArrayList<String> list = new ArrayList<String>();
                    JSONArray jsonArray = (JSONArray) jObj;
                    if (jsonArray != null) {
                        int len = jsonArray.length();
                        for (int i = 0; i < len; i++) {
                            list.add(jsonArray.get(i).toString());
                            System.out.println(list.get(i) + "\n");

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
                    e.printStackTrace();
                }
            }
        };
    }


    public static Double[] processString (String s){
        //System.out.println("start string: "+s);
        double latitude = 0.0;
        double longitude = 0.0;

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        ArrayList<String> sArr = new ArrayList<>();

        while (m.find()) {
            System.out.println(m.group());
            sArr.add(m.group());
        }

        System.out.println(sArr.toString());
        //latitude = Double.parseDouble((sArr.get(0) + '.' + sArr.get(1)));
        System.out.println("latitude = " + latitude);
        //longitude = Double.parseDouble((sArr.get(2) + '.' + sArr.get(3)));
        System.out.println("longitude = " + longitude);
        //int id = Integer.parseInt(sArr.get(4));

        Double[] retArr = new Double[2];
        retArr[0] = latitude;
        retArr[1] = longitude;
        return retArr;

    }
}
