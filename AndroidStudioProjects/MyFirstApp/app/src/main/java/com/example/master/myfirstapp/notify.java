package com.example.master.myfirstapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Master on 19-Mar-17.
 */

public class notify extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    String closestPet1 ="Error can't connect";
    long Globid =-1;
    double distToUser=-1;
    double userLongitude;
    double userLatitude;






    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String notificationPeriod = sharedPrefs.getString("notification_period", "Weekly");

        //Toast.makeText(this, ""+TimeUnit.DAYS.toMillis(Integer.parseInt(notificationPeriod)), Toast.LENGTH_LONG).show();


        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                getNotified();
                Long period = TimeUnit.DAYS.toMillis(Integer.parseInt(notificationPeriod));
                handler.postDelayed(runnable, period/24);
                //1 second = 1000
                //10 second = 10000
                //3600000
            }
        };

        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        //makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }

    public void getNotified(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String notificationRadius = sharedPrefs.getString("notification_radius", "15");

        System.out.println("getting notification");
        String url = "http://www.doglost.co.uk/dog-blog.php?dogId=";

        //Toast.makeText(this, getClosest().first, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, getClosest().third.toString() + " " +Double.parseDouble(notificationRadius)/100, Toast.LENGTH_LONG).show();

        /**
         * IMPORTANT - 0.63 returned as closet in getclosest.third means the closest is 63km away so divide by 100
         */

        Intent i = new Intent(Intent.ACTION_VIEW);

        //Check connection before notification
        if(getClosest().first.toString().equals("Error can't connect")){
            System.out.println("ERROR CAN'T CONNECT");
            return;
        }

        //Check if the closest is within their set notification radius
        if(getClosest().third > Double.parseDouble(notificationRadius)/100){
            System.out.println("OUTSIDE USER PREFERENCE RADIUS");
            return;
        }
        else{
            System.out.println("NAME TO STRING: " + getClosest().first.toString());
        }
        i.setData(Uri.parse(url+getClosest().second.toString()));

        PendingIntent ci = PendingIntent.getActivity(context,0, i, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //closest = ((MapPanePetActivity)getActivity())

        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.map_marker_paw)
                .setContentTitle("Someone lost their pet near you!")
                .setContentText(getClosest().first)
                .setContentIntent(ci)
                .getNotification();
        notificationmgr.notify(0,notif);
    }


    public Triplet<String, Long, Double> getClosest(){

        TrackGPS gps2;
        gps2 = new TrackGPS(this);

        if(gps2.canGetLocation()) {
            userLongitude = gps2.getLongitude();
            userLatitude = gps2.getLatitude();
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jObj = new JSONArray(response);
                    GPSTestResultsArr[] GPStra = new GPSTestResultsArr[jObj.length()];

                    double closestPetDist = Double.POSITIVE_INFINITY;
                    String closestPet = "";
                    long id1 = -1;


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
                        String imageAddress = jobj.getString("imageAddress");
                        double distancefromUser = (Math.pow(Math.pow(userLatitude - latitude, 2) + Math.pow(userLongitude - longitude, 2), 0.5));
                        //System.out.println(name+" Distance from user: "+distancefromUser);

                        if (distancefromUser < closestPetDist) {
                            closestPetDist = distancefromUser;
                            closestPet = name;
                            id1= id;
                            closestPet1 = name;
                            Globid = id;
                            distToUser = distancefromUser;
                        }

                    }
                    if (closestPet != null) {
                        closestPet1 = closestPet;
                        Globid = id1;
                        //Toast.makeText(getApplicationContext(),"in onresponse "+ closestPet1, Toast.LENGTH_SHORT).show();

                    }
                    System.out.println("closestPet1: " + closestPet1 + " closestPet " + closestPet);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String[] array = new String[0];
        GetLatLongsRequest getLLR = new GetLatLongsRequest(array, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getLLR);
        //Toast.makeText(getApplicationContext(), "returned "+closestPet1, Toast.LENGTH_SHORT).show();

        return new Triplet<String, Long, Double>(closestPet1, Globid, distToUser);

    }
    public class Triplet<T, U, V> {

        private final T first;
        private final U second;
        private final V third;

        public Triplet(T first, U second, V third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public T getFirst() { return first; }
        public U getSecond() { return second; }
        public V getThird() { return third; }
    }

}
