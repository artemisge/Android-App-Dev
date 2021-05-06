package com.example.meditation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
                Intent intent = new Intent(MainActivity.this,Calendar.class);
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

    public void meditate(View view)
    {
        Intent i=new Intent(this,Meditate.class);

        startActivity(i);
    }

}