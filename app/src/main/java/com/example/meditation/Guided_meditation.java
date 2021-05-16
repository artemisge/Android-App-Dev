package com.example.meditation;

import android.content.Intent;
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
    ArrayList arrayList;
    ArrayAdapter myAdapter;
    MediaPlayer myMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_meditation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        listView = findViewById(R.id.listViewMed);
        arrayList = new ArrayList<String>();
        Field[] fields = R.raw.class.getFields();
        for(int i=0; i<fields.length; i++){
            arrayList.add(fields[i].getName());
        }

        myAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(myMediaPlayer!=null)
                    myMediaPlayer.release();

                int resId = getResources().getIdentifier((String) arrayList.get(position), "raw", getPackageName());
                if(resId==myMediaPlayer.getAudioSessionId())
                    myMediaPlayer.release();
                else {
                    myMediaPlayer = MediaPlayer.create(Guided_meditation.this, resId);
                    myMediaPlayer.start();
                }

            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        if(myMediaPlayer!=null)
            myMediaPlayer.release();
    }
}