package com.example.master.LostPets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import java.util.regex.*;


public class MapPanePetActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

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
    private ProgressDialog dialogMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialogMap = new ProgressDialog(this);
        dialogMap.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogMap.setMessage("Loading. Please wait...");
        dialogMap.setIndeterminate(true);
        dialogMap.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

            }
        }, 1000);
        closestPet1="create VALUE";
        System.out.println("CREATED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_pane_pet);


        gps = new TrackGPS(MapPanePetActivity.this);


        if(gps.canGetLocation()) {
            userLongitude = gps.getLongitude();
            userLatitude = gps.getLatitude();
        }else{
            Toast.makeText(getApplicationContext(), "Can't get user location", Toast.LENGTH_SHORT).show();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gps.canGetLocation){
                    mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(),gps.getLongitude()) , 10.0f) );
                }
            }
        });


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

                    System.out.println("before adding markers" + mClusterManager.getRenderer());
                    for (int i = 0; i < jObj.length(); i++) {

                        JSONObject jobj = jObj.getJSONObject(i);
                        String name = jobj.getString("Name");
                        //System.out.println("MapPanePetActivity : name = " + name);
                        String gender = jobj.getString("Gender");
                        //System.out.println("MapPanePetActivity : Gender = " + gender);
                        String regLost = jobj.getString("RegionLost");
                        //System.out.println("MapPanePetActivity : RegionLost = " + regLost);
                        String postCode = jobj.getString("PostCode");
                        //System.out.println("MapPanePetActivity : Postcode = " + postCode);
                        String breed = jobj.getString("Breed");
                        //System.out.println("MapPanePetActivity : Breed = " + breed);
                        double latitude = jobj.getDouble("Latitude");
                        //System.out.println("MapPanePetActivity : latitude = " + latitude);
                        double longitude = jobj.getDouble("Longitude");
                        //System.out.println("MapPanePetActivity : longitude = " + longitude);
                        long id = jobj.getLong("ID");
                        //System.out.println("MapPanePetActivity : id = " + id);
                        String imageAddress = jobj.getString("imageAddress");
                        //System.out.println("MapPanePetActivity : imageAddress = " + imageAddress);
                        double distancefromUser = ( Math.pow(Math.pow(userLatitude-latitude,2)+Math.pow(userLongitude-longitude,2),0.5) );
                        //System.out.println("Distance from user "+distancefromUser);

                        if(distancefromUser<closestPetDist){
                            closestPetDist = distancefromUser;
                            closestPet = name;
                        }

                        GPStra[i] = new GPSTestResultsArr(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);

                        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(String.valueOf(name)).snippet(breed+" " + regLost+" " +id).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_paw)));
                        addItems(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);

                        StoreValsClass svc = new StoreValsClass();
                        StoreValsClass.addSet(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);
                    }
                    if(closestPet != null){
                        closestPet1=closestPet;
                    }
                    System.out.println("closestPet1: "+closestPet1+" closestPet "+closestPet);
                    setClosestPet(closestPet1);
                    mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(userLatitude,userLongitude) , 10.0f) );
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

                        }
                    }
                    if(dialogMap != null){
                        dialogMap.dismiss();
                    }


                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapPanePetActivity.this);
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
        RequestQueue queue = Volley.newRequestQueue(MapPanePetActivity.this);
        queue.add(getLLR);

        System.out.println("PARAMS: \n \n \n");
        //System.out.println(getLLR.getParams());

        System.out.println("setUp call");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Toast.makeText(getApplicationContext(), "Tap on any marker, then tap on the name for more information!", Toast.LENGTH_LONG).show();
    }

    public void setClosestPet(String closest){
        closestPet1 = closest;
    }
    public void getClosest(){

        Intent data = new Intent();
        data.putExtra("myData1", "Data 1 value");
        data.putExtra("myData2", "Data 2 value");
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void addPawClicked(View view) {

         Intent intent = new Intent(MapPanePetActivity.this, PrimaryCardView.class);
           startActivity(intent);
        System.out.println("add paw was clicked");
    }

    public static Double[] processString(String s) {
        //System.out.println("start string: "+s);
        double latitude = 0.0;
        double longitude = 0.0;

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        ArrayList<String> sArr = new ArrayList<>();

        while (m.find()) {
         //   System.out.println(m.group());
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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        if(userLatitude != 0.0 && userLongitude != 0.0){
            mMap.addMarker(new MarkerOptions().position(new LatLng(userLatitude, userLongitude)).title("You are here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
        setUpClusterer();


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                System.out.println("Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            System.out.println("Can't find style.");
        }

        /**
         * REMOVED LONG CLICK FUNCTIONALITY FOR NOW
         */
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        System.out.println("CLICKED ON MARKER");
        //Toast.makeText(getApplicationContext(), marker.getSnippet().substring(marker.getSnippet().length()-6), Toast.LENGTH_SHORT).show();
        String url = "http://www.doglost.co.uk/dog-blog.php?dogId=" +marker.getSnippet().substring(marker.getSnippet().length()-6);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private ClusterManager<MyClusteringItem> mClusterManager;

    private void setUpClusterer() {

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this, mMap);
        mClusterManager.setRenderer(new CustomClusterRenderer(this, mMap, mClusterManager) );
        System.out.println("after setting renderer" + mClusterManager.getRenderer());

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        System.out.println("set listeners");
    }

    private void addItems(String name, String gender, String regLost, String postCode, String breed, double latitude, double longitude, long id, String imageAddress) {
        MyClusteringItem offsetItem = new MyClusteringItem(name, gender, regLost, postCode, breed, latitude, longitude, id, imageAddress);
        mClusterManager.addItem(offsetItem);
    }
}


