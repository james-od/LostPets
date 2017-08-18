package com.example.master.LostPets;


import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button = new Button(this);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        ListView v = getListView();
        v.addFooterView(button);
        button.setText("Tap to send feedback");
        button.setBackgroundColor(Color.WHITE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);

                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"lostpetsmail@gmail.com"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "LostPets Feedback");
                startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
            }
        });


    }


}