package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText et = findViewById(R.id.your_name);
        et.setText("yoo");//MainActivity.dbHelper.getName());
        //MainActivity.dbHelper.changeName(et.getText().toString());

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
                // guess what... EDIT NAME
                EditText et = findViewById(R.id.your_name);
                et.setEnabled(true);
                edit_name.setVisibility(View.GONE);
                ImageButton edit_name_done = findViewById(R.id.edit_name_done);
                edit_name_done.setVisibility(View.VISIBLE);
                et.setCursorVisible(true);
            }
        });

        ImageButton edit_name_done = findViewById(R.id.edit_name_done);
        edit_name_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // guess what... EDIT NAME
                EditText et = findViewById(R.id.your_name);
                // update database
                MainActivity.dbHelper.setName(et.getText().toString());
                et.setEnabled(false);

                edit_name_done.setVisibility(View.GONE);
                ImageButton edit_name = findViewById(R.id.edit_name);
                edit_name.setVisibility(View.VISIBLE);
                et.setCursorVisible(false);
            }
        });
/*
        TextView set_reminder = findViewById(R.id.set_reminder);
        set_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String string = MainActivity.dbHelper.getName();
                Toast.makeText (Profile.this, "test: ", Toast.LENGTH_SHORT).show();
                List<String> list = MainActivity.dbHelper.fetchData2();
                Toast.makeText (Profile.this, list.toString(), Toast.LENGTH_LONG).show();
            }
        });*/

    }
    // clickable Text View. Can't be assigned an OnClickListener :(
    public void reminder_function(View v) {
        Toast.makeText (Profile.this, "test: ", Toast.LENGTH_SHORT).show();
        //List<String> list = MainActivity.dbHelper.fetchData2();
        //Toast.makeText (Profile.this, list.toString(), Toast.LENGTH_LONG).show();
    }
}

/*// TO BE DELETED/EDITED -> test the database
        Button testButton = findViewById(R.id.test_counter);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add to database -> a new meditation entry
                boolean success = MainActivity.dbHelper.addMeditation(12, 12, 5, 2021);

                Toast.makeText (Solo_meditation.this, "MED ENTRY ADDED: " + success, Toast.LENGTH_SHORT).show();
                List<String> list = MainActivity.dbHelper.fetchData();
                Toast.makeText (Solo_meditation.this, list.toString(), Toast.LENGTH_LONG).show();

            }
        });*/