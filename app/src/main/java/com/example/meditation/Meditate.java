package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Meditate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //code for the navbar at the bottom
        //the same throughout the app
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(Meditate.this, Calendar.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(Meditate.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(Meditate.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;

            }
        });

        Button guidedButton = (Button)findViewById(R.id.guided_meditation);
        //move to guided meditation activity
        guidedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Meditate.this, Guided_meditation.class);
                startActivity(intent1);
            }
        });

        Button soloButton = (Button)findViewById(R.id.solo_meditation);
        //move to solo meditation activity
        soloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Meditate.this, Solo_meditation.class);
                startActivity(intent2);
            }
        });

    }
}