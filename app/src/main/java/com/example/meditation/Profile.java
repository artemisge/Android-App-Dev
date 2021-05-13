package com.example.meditation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.List;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tv = findViewById(R.id.your_name);
        tv.setText(MainActivity.dbHelper.getName());

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

        TextView clear_database = findViewById(R.id.clear_database);
        clear_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.dbHelper.clearDatabase();
                Toast.makeText (Profile.this, "cleared! ", Toast.LENGTH_LONG).show();
            }
        });

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
//
//<TextView
//    android:id="@+id/your_name"
//    android:layout_width="wrap_content"
//    android:layout_height="wrap_content"
//    android:layout_gravity="center"
//    android:layout_marginTop="56dp"
//    android:fontFamily="cursive"
//    android:text="Name"
//    android:textColor="#757575"
//    android:textSize="35sp"
//    app:layout_constraintEnd_toEndOf="parent"
//    app:layout_constraintStart_toStartOf="parent"
//    app:layout_constraintTop_toBottomOf="@+id/imageButton" />