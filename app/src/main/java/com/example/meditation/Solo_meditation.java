package com.example.meditation;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Solo_meditation extends AppCompatActivity {
    private EditText myEditTextInput;  //set minutes edit text
    private TextView myTextViewCountDown; //text view of count down timer
    private Button myButtonSet;   //set minutes button
    private Button myButtonStartPause;  //start/pause button
    private Button myButtonReset;  //reset button

    private CountDownTimer mCountDownTimer;
    private boolean myTimerRunning; //if the time is running or not

    private long myStartTimeInMillis; //start time
    private long myTimeLeftInMillis;  //time left
    private long myEndTime; //time the timer is supposed to end

    private Toast meditationEnded;

    private SoundPool soundPool;
    private int alarm;

    //pass this to calendar
    String pattern = "MM-dd-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_meditation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //to have a back button at the top

        //this code is for the navbar at the bottom, it is the same throughout the app
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(Solo_meditation.this, CalendarActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(Solo_meditation.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(Solo_meditation.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }

        });

        myEditTextInput=findViewById(R.id.edit_text_input_min);
        myTextViewCountDown = findViewById(R.id.text_view_countdown);

        myButtonSet=findViewById(R.id.button_set);
        myButtonStartPause = findViewById(R.id.button_start_pause);
        myButtonReset = findViewById(R.id.button_reset);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //new way of creating sound pool for API 21 or higher
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0); //old way for creating sound pool
        }
        //the alarm will play after meditation session ends
        alarm = soundPool.load(this, R.raw.alarm, 1);

        //for setting the time
        myButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = myEditTextInput.getText().toString();
                //time cannot be set to zero
                if (input.length() == 0) {
                    Toast.makeText(Solo_meditation.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(Solo_meditation.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                myEditTextInput.setText("");
            }
        });

        //when press the start/pause button
        myButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the timer is running we want to pause it
                if (myTimerRunning) {
                    pauseTimer();
                } else {
                    // check if time in set isn't zero
                    String input = myEditTextInput.getText().toString();
                    //if timer is not running and is not set to 0, timer starts
                    if (myStartTimeInMillis != 0) {
                        startTimer();

                        // add meditation in database if it is the first time meditating
                        // in that day
                        String currentDay = simpleDateFormat.format(new Date());
                        if (!MainActivity.dbHelper.checkDay(currentDay)) {
                             MainActivity.dbHelper.addMeditation((currentDay));

                            try {
                                MainActivity.dbHelper.updateStreak(currentDay);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            MainActivity.dbHelper.updateAwards();
                        }
                    }
                }
            }
        });

        //when press reset button, timer resets
        myButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();
    }


    //method to set the time
    private void setTime(long milliseconds) {
        myStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard(); //close keyboard after time is set
    }

    //this method is for the keyboard to close automatically after we have typed what we wanted in
    //the set minutes field
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void startTimer() {
        myEndTime = System.currentTimeMillis() + myTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(myTimeLeftInMillis, 1000) { //the timer will run for as much as the time left and will update every second
            @Override
            public void onTick(long millisUntilFinished) {
                myTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                myTimerRunning = false; //time is not running anymore

                //toast the meditation session is completed
                meditationEnded=Toast.makeText(getApplicationContext(),"You did it! \n Meditation session ended.", Toast.LENGTH_SHORT);
                meditationEnded.show();

                //alarm play
                soundPool.play(alarm,1,1,0,0,1);

                updateInterface();
            }
        }.start(); //as soon as the startTimer method is called the count down timer will start
        myTimerRunning = true; //the time is running
        updateInterface();
    }

    //method to pause the timer
    private void pauseTimer() {
        mCountDownTimer.cancel(); //cancel the timer
        myTimerRunning = false; //time is not running
        updateInterface();
    }

    private void resetTimer() {
        myTimeLeftInMillis = myStartTimeInMillis;
        updateCountDownText();
        updateInterface();
    }

    //method to update the text of the count down timer
    private void updateCountDownText() {
        int hours = (int) (myTimeLeftInMillis / 1000) / 3600; //turn milliseconds into hours
        int minutes = (int) ((myTimeLeftInMillis / 1000) % 3600) / 60; //turn milliseconds into minutes
        int seconds = (int) (myTimeLeftInMillis / 1000) % 60; //turn milliseconds into seconds

        //create the string to display the time of the count down timer in the right format
        String timeLeftFormatted;
        //if the timer is set to less than an hour, hours won't show
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        myTextViewCountDown.setText(timeLeftFormatted);
    }

    //updates the buttons and edit texts
    private void updateInterface() {
        if (myTimerRunning) {
            myEditTextInput.setVisibility(View.INVISIBLE);
            myButtonSet.setVisibility(View.INVISIBLE);
            myButtonReset.setVisibility(View.INVISIBLE);
            myButtonStartPause.setText("Pause");
        } else {
            myEditTextInput.setVisibility(View.VISIBLE);
            myButtonSet.setVisibility(View.VISIBLE);
            myButtonStartPause.setText("Start");
            if (myTimeLeftInMillis < 1000) {
                myButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                myButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (myTimeLeftInMillis < myStartTimeInMillis) {
                myButtonReset.setVisibility(View.VISIBLE);
            } else {
                myButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }


    //methods to make the countdown timer work when device rotate
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", myTimeLeftInMillis);
        outState.putBoolean("timerRunning", myTimerRunning);
        outState.putLong("endTime", myEndTime);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { //will be called after onCreate if there is a saved instance state
        super.onRestoreInstanceState(savedInstanceState);
        myTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        myTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateInterface();
        if (myTimerRunning) { //if timer still running start the timer
            myEndTime = savedInstanceState.getLong("endTime");
            myTimeLeftInMillis = myEndTime - System.currentTimeMillis();
            startTimer();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}

