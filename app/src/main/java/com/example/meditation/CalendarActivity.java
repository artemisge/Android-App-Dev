package com.example.meditation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

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
                        //Intent intent1 = new Intent(CalendarActivity.this, Calendar.class);
                        //startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(CalendarActivity.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(CalendarActivity.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });
    }

    public List<EventDay> calendarDays(){
        // returns the total days meditated in EventDays for the Calendar widget to mark
        List <EventDay> events = new ArrayList<>();
        List <int[]> intDays = MainActivity.dbHelper.getCalendarDays(); //an array of integers from the db representing the dates which the user meditated
        int[] currentDay;
        int day, month, year;

        for(int i=0; i<intDays.size(); i++){
            currentDay = intDays.get(i);
            day = currentDay[0];
            month = currentDay[1] +1; //the +1 is done because Calendar objects use 0 indexing for months while the database doesn't
            year = currentDay[2];

            Calendar calendar = Calendar.getInstance(); //creating an instance of a Calendar object to use the non static method set() to set the date
            calendar.set(day, month, year);
            events.add(new EventDay(calendar, R.drawable.ic_baseline_circle_24)); //adding the event day to the array with the icon we wish to display on the calendar
        }
        return events;

    }

    public void updateStats() {
        // updates the stats on the activity
        TextView streak = (TextView) findViewById(R.id.streak);
        TextView total_time = (TextView) findViewById(R.id.total_days);
        CalendarView cal = (CalendarView) findViewById(R.id.calendarView);

        cal.setEvents(calendarDays());
        //fetches the data from the db
        streak.setText(Integer.toString(MainActivity.dbHelper.getStreak()));
        total_time.setText(Integer.toString(MainActivity.dbHelper.getTotalDays()));


    }
}