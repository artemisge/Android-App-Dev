package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static DBHelper dbHelper; // to be accessible from any class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DATABASE reference
        dbHelper= new DBHelper(MainActivity.this);

        // BOTTOM NAV BAR
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(MainActivity.this, CalendarActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent2);
                        break;
                }

                return false;
            }
        });


        // go to meditation menu activity
        ImageButton lotusButton = findViewById(R.id.lotus_button);
        lotusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Meditate.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}