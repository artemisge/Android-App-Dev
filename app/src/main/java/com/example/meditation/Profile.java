package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tv = findViewById(R.id.your_name);
        tv.setText(MainActivity.dbHelper.getName());

        loadAwards();
        // also do: get awards

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(Profile.this, Calendar.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        //Intent intent2 = new Intent(MainActivity.this, Profile.class);
                        //startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(Profile.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

        ImageButton edit_name = findViewById(R.id.edit_name);
        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.your_name);
                et.setFocusableInTouchMode(true);
                et.setFocusable(true);
                et.setEnabled(true);
                et.setCursorVisible(true);
                edit_name.setVisibility(View.GONE);
                ImageButton edit_name_done = findViewById(R.id.edit_name_done);
                edit_name_done.setVisibility(View.VISIBLE);
            }
        });

        ImageButton edit_name_done = findViewById(R.id.edit_name_done);
        edit_name_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // guess what... EDIT NAME
                EditText et = findViewById(R.id.your_name);
                String new_name = et.getText().toString();

                et.setEnabled(false);
                et.setFocusableInTouchMode(false);
                et.setFocusable(false);
                et.setCursorVisible(false);

                Toast.makeText (Profile.this, "name changed to: " + new_name, Toast.LENGTH_SHORT).show();

                MainActivity.dbHelper.setName(new_name);

                edit_name_done.setVisibility(View.GONE);
                ImageButton edit_name = findViewById(R.id.edit_name);
                edit_name.setVisibility(View.VISIBLE);
                et.setCursorVisible(false);
            }
        });

        TextView awards = findViewById(R.id.awards);
        awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String string = MainActivity.dbHelper.getName();
                //Toast.makeText (Profile.this, "test: ", Toast.LENGTH_SHORT).show();

                boolean success = MainActivity.dbHelper.addMeditation(12, 12, 5, 2021);

                Toast.makeText (Profile.this, "MED ENTRY ADDED: " + success, Toast.LENGTH_SHORT).show();
            }
        });

        // AWARDS
        ImageView week_straight = findViewById(R.id.one_day);
        week_straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText (Profile.this, "First time meditating: ACHIEVEMENT LOCKED ", Toast.LENGTH_LONG).show();
            }
        });
        ImageView month_straight = findViewById(R.id.three_days);
        month_straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText (Profile.this, "Three days straight meditating: ACHIEVEMENT LOCKED ", Toast.LENGTH_LONG).show();
            }
        });
        ImageView half_hour_straight = findViewById(R.id.one_week);
        half_hour_straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText (Profile.this, "One week straight meditating: ACHIEVEMENT LOCKED ", Toast.LENGTH_LONG).show();
            }
        });
        ImageView one_hour_straight = findViewById(R.id.one_month);
        one_hour_straight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText (Profile.this, "One month straight meditating: ACHIEVEMENT LOCKED ", Toast.LENGTH_LONG).show();
            }
        });


        TextView set_reminder = findViewById(R.id.set_reminder);
        set_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String string = MainActivity.dbHelper.getName();
                //Toast.makeText (Profile.this, "test: ", Toast.LENGTH_SHORT).show();
                List<String> list = MainActivity.dbHelper.fetchData2();
                Toast.makeText (Profile.this, "all data: " + list.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loadAwards() {
        ImageView iv1 = findViewById(R.id.one_day);
        ImageView iv2 = findViewById(R.id.three_days);
        ImageView iv3 = findViewById(R.id.one_week);
        ImageView iv4 = findViewById(R.id.one_month);

        // returns 4-slot boolean array
        Boolean[] unlocked = MainActivity.dbHelper.loadAwards();
        if (unlocked[0]) {

        }
        if (unlocked[1]) {

        }
        if (unlocked[2]) {

        }
        if (unlocked[3]) {

        }
    }
}
