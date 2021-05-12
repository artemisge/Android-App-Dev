package com.example.meditation;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Locale;

public class Solo_meditation extends AppCompatActivity {
    private EditText myEditTextInput;
    private TextView myTextViewCountDown;
    private Button myButtonSet;
    private Button myButtonStartPause;
    private Button myButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean myTimerRunning;

    private long myStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long myEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_meditation);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(Solo_meditation.this, Calendar.class);
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

        myButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = myEditTextInput.getText().toString();
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


        myButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        myButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
        updateCountDownText();
    }

    private void setTime(long milliseconds) {
        myStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void startTimer() {
        myEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                myTimerRunning = false;
                updateInterface();
            }
        }.start();
        myTimerRunning = true;
        updateInterface();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        myTimerRunning = false;
        updateInterface();
    }
    private void resetTimer() {
        mTimeLeftInMillis = myStartTimeInMillis;
        updateCountDownText();
        updateInterface();
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),  //if time is less than an hour then tey disappear
                    "%02d:%02d", minutes, seconds);
        }
        myTextViewCountDown.setText(timeLeftFormatted);
    }
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
            if (mTimeLeftInMillis < 1000) {
                myButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                myButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < myStartTimeInMillis) {
                myButtonReset.setVisibility(View.VISIBLE);
            } else {
                myButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", myTimerRunning);
        outState.putLong("endTime", myEndTime);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        myTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateInterface();
        if (myTimerRunning) {
            myEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = myEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
}

