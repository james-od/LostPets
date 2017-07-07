package com.example.master.myfirstapp;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ImageView exampleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ImageView bmImage = new ImageView(getApplicationContext());


        //START NOTIFICATION SERVICE
        System.out.println("Trying to start service");
        startService(new Intent(this, notify.class));

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Lost Pets");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        //exampleImage = (ImageView) findViewById(R.id.exampleImage);
        //String url = "https://www.james-odonnell.com/LostPets/images/floofsky1.jpeg";
        //new DownloadImageTask(exampleImage).execute(url);

       // exampleImage = (ImageView) findViewById(R.id.headerDog);
        String url2 = "https://www.james-odonnell.com/LostPets/images/petsbanner.jpg";
        //String url2 = "https://www.james-odonnell.com/LostPets/scrapedImages/images/254.jpg";
        //new DownloadImageTask(exampleImage).execute(url2);

        ImageView imageView = (ImageView) findViewById(R.id.headerDog);

        Picasso.with(this).load(url2).into(imageView);
        //getNotified(viewPager);

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            //pDlg.dismiss();
            bmImage.setImageBitmap(result);
        }

    }

    /*
    public void getNotified(View view){
        System.out.println("getting notification");

        NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.map_marker_paw)
                .setContentTitle("Someone lost their pet near you!")
                .setContentText("X lost Y here recently, tap for more information!")
                .getNotification();
        notificationmgr.notify(0,notif);
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_SeeLostPets:
                //noinspection SimplifiableIfStatement


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void AddAMissingPet(View view) {

        Intent intent = new Intent(MyActivity.this, AddPetActivity.class);
        startActivity(intent);
    }

    public void ReportALostPet(View view) {

        Intent intent = new Intent(MyActivity.this, NotificationSettingsActivity.class);
        startActivity(intent);
    }

    public void HowDoesItWork(View view) {

        Intent intent = new Intent(MyActivity.this, HowDoesItWorkActivity.class);
        startActivity(intent);
    }

    public void viewList(View view) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MyActivity.this, MapService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dialog.cancel();
            }
        }, 1000);
    }

    public void Settings(View view) {

        Intent intent = new Intent(MyActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void ContactUs(View view) {

        //changed from ContactUsActivity to testing LoginActivity
        Intent intent = new Intent(MyActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void ShowMap(View view){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MyActivity.this, MapPanePetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
                dialog.cancel();
            }
        }, 1000);

    }

    private TabLayout tabLayout;
    private ViewPager viewPager;


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        //     adapter.addFragment(new TwoFragment(), "TWO");
        //      adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
//AIzaSyDUE2xyKNhNpF6_gidv0ISCF0JDLh4ID8A


