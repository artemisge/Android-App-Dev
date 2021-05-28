package com.example.meditation;

import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        updateStats();
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        List <int[]> intDays = MainActivity.dbHelper.getCalendarDays(); //an array of integers from the db representing the dates which the user meditated
        int[] currentDay;
        int day, month, year;
        Calendar cal = Calendar.getInstance();

        //loop to mark the days on the calendar
        for(int i=0; i<intDays.size(); i++) {
            currentDay = intDays.get(i);
            day = currentDay[0];
            month = currentDay[1] -1; //-1 is because calendar's months are 0-based while the databases aren't
            year = currentDay[2];
            cal.set(year, month, day);
            Date date = cal.getTime(); //the first getTime is to convert the Calendar object to a Date object

            Event ev = new Event(Color.argb(255, 154, 115, 171),  date.getTime()); //the second getTime is to convert the Date object into an long Epoch
            compactCalendar.addEvent(ev);
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
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
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //adds the month and year on top of the calendar
                TextView month = (TextView) findViewById(R.id.textView4);
                month.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }


    public void updateStats() {
        // updates the stats on the activity
        TextView streak = (TextView) findViewById(R.id.streak);
        TextView total_time = (TextView) findViewById(R.id.total_days);

        //fetches the data from the db
        streak.setText(Integer.toString(MainActivity.dbHelper.getStreak()));
        total_time.setText(Integer.toString(MainActivity.dbHelper.getTotalDays()));


    }
}