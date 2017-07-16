package com.james_odonnell.loginbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        final EditText etUsername = (EditText) findViewById(R.id.etName);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        String message = name + " welcome to user area";
        welcomeMessage.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
        }
}
