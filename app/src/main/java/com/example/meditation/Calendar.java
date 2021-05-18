package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // added for clickability
        //TextView title = (TextView) findViewById(R.id.your_name);
        //title.setText("Calendar activity");
        updateStats();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        //Intent intent1 = new Intent(Calendar.this, Calendar.class);
                        //startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(Calendar.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(Calendar.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });
    }

    public void updateStats() {
        // fetch data from database
        TextView streak = (TextView) findViewById(R.id.streak);
        TextView total_time = (TextView) findViewById(R.id.total_days);

        streak.setText(Integer.toString(MainActivity.dbHelper.getStreak()));
        total_time.setText(Integer.toString(MainActivity.dbHelper.getTotalDays()));


    }
}