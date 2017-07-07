package com.example.master.myfirstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class NotificationSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modifySettings=new Intent(NotificationSettingsActivity.this,SettingsActivity.class);
                startActivity(modifySettings);
            }
        });
        */

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        StringBuilder builder = new StringBuilder();

        builder.append("\n" + "Notification frequency:\t" + sharedPrefs.getString("notification_period", "Weekly"));
        builder.append("\n" + "Notification radius:\t" + sharedPrefs.getString("notification_radius", "15"));
        builder.append("\n" + "Sync Intervals:\t" + sharedPrefs.getString("sync_interval", "-1"));
        builder.append("\n" + "Name:\t" + sharedPrefs.getString("full_name", "Not known to us"));
        builder.append("\n" + "Email Address:\t" + sharedPrefs.getString("email_address", "No EMail Address Provided"));
        builder.append("\n" + "Customized Notification Ringtone:\t" + sharedPrefs.getString("notification_ringtone", ""));
        builder.append("\n\nClick on Settings Button at bottom right corner to Modify Your Prefrences");

        TextView settingsTextView = (TextView) findViewById(R.id.settingsContent);
        settingsTextView.setText(builder.toString());


    }


}

