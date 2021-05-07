package com.example.meditation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Guided_meditation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_meditation);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(Guided_meditation.this, Calendar.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(Guided_meditation.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(Guided_meditation.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

    }
}
