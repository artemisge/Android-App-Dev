package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(MainActivity.this, Calendar.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        //Intent intent3 = new Intent(MainActivity.this, Home.class);
                        //startActivity(intent3);
                        break;
                }

                return false;
            }
        });


    }

    public void meditate(View view)
    {
        Intent i=new Intent(this,Meditate.class);

        startActivity(i);
    }
    /*
    // equivalent to hamburger menu -> no no
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    } */

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case  R.id.Calendar:
//                Intent intent = new Intent(MainActivity.this,Calendar.class);
//                startActivity(intent);
//                return true;
//            case R.id.Profile:
//                Intent intent2 = new Intent(MainActivity.this,Profile.class);
//                startActivity(intent2);
//                return true;
//            case R.id.Home:
//                //Intent intent3 = new Intent(MainActivity.this,Home.class);
//                //startActivity(intent3);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }



}