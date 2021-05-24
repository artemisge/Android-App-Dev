package com.example.meditation;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Guided_meditation extends AppCompatActivity {

    ListView listView;
    ArrayList arrayList, names;
    ArrayAdapter myAdapter;

    //for the navbar at the bottom
    //same throughout the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_meditation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
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

        listView = findViewById(R.id.listViewMed);
        arrayList = new ArrayList<String>();
        names=new ArrayList<String>();
        Field[] fields = R.raw.class.getFields(); //get the audio files from raw directory
        String nameOfSession;

        //get the names of the audio files in the right format and add them to an array list
        for(int i=1; i<fields.length; i++){ //skip the alarm audio file
            //add names of audio files to pass it later to play activity
            arrayList.add(fields[i].getName());
            //modify name of the sessions and them to an array list for display purposes
            nameOfSession=fields[i].getName().replace("_"," ");
            nameOfSession=nameOfSession.substring(0,1).toUpperCase() + nameOfSession.substring(1);
            names.add(nameOfSession);
        }

        //display the names of the audio files(guided meditation sessions) as a list
        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String meditation_name = listView.getItemAtPosition(position).toString();
                startActivity(new Intent(Guided_meditation.this, PlayActivity.class)

                        .putExtra("pos", position).putExtra("sessions", arrayList).putExtra("session_name", meditation_name));
            }
        });
    }


}