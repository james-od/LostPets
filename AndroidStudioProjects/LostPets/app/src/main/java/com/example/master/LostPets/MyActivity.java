package com.example.master.LostPets;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE <- what?";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private boolean isInFront;
    ImageView exampleImage;
    private ProgressDialog dialog;
    private ProgressDialog dialogMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ProgressDialog dialog = new ProgressDialog(this);
        final ProgressDialog dialogMap = new ProgressDialog(this);
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ImageView bmImage = new ImageView(getApplicationContext());


        //Set default preferences by first checking if they're populated
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPrefs.getString("notification_period", null) == null){
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("notification_period", "3");
            editor.apply();
        }
        if(sharedPrefs.getString("notification_radius", null) == null){
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("notification_radius", "15");
            editor.apply();
        }

        //START NOTIFICATION SERVICE
        System.out.println("Trying to start service");
        startService(new Intent(this, notify.class));

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        //CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("Lost Pets");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MyActivity.this, DataService.class);
                startService(intent);
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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Hi!")
                        .setMessage("For the best experience with our app enable please allow LostPets to access your location.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MyActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        99);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        99);
            }
        }else{

            dialogMap = new ProgressDialog(this);
            dialogMap.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialogMap.setMessage("Loading. Please wait...");
            dialogMap.setIndeterminate(true);
            dialogMap.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(MyActivity.this, MapPanePetActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }, 1000);
        }
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            StoreValsClass svc = (StoreValsClass) getIntent().getExtras().getParcelable("dummy");
            String message = svc.toString();
            Toast.makeText(context, "bleh " +message, Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        if (dialog != null) {
            dialog.dismiss();
        }
        if (dialogMap != null) {
            dialogMap.dismiss();
        }    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
//AIzaSyDUE2xyKNhNpF6_gidv0ISCF0JDLh4ID8A


