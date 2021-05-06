package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    // test 22352
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case  R.id.Calendar:
                Intent intent = new Intent(MainActivity.this,Calendar.class;
                startActivity(intent);
                return true;
            case R.id.Profile:
                return true;
            case R.id.Home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}