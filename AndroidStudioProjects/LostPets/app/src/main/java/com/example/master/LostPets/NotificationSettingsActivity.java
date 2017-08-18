package com.example.master.LostPets;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class NotificationSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        StringBuilder builder = new StringBuilder();

        builder.append("\n" + "Notification frequency:\t" + sharedPrefs.getString("notification_period", null));
        builder.append("\n" + "Notification radius:\t" + sharedPrefs.getString("notification_radius", null));
        builder.append("\n" + "Sync Intervals:\t" + sharedPrefs.getString("sync_interval", "-1"));
        builder.append("\n" + "Customized Notification Ringtone:\t" + sharedPrefs.getString("notification_ringtone", ""));
        builder.append("\n\nClick on Settings Button at bottom right corner to Modify Your Prefrences");

        TextView settingsTextView = (TextView) findViewById(R.id.settingsContent);
        settingsTextView.setText(builder.toString());


    }


}

