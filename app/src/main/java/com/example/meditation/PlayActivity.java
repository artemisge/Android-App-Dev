package com.example.meditation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class PlayActivity extends AppCompatActivity {

    Button btnplay,btnnext,btnprev;
    TextView txtname;
    SeekBar seekbar;

    ArrayList<String> mySessions;
    String sessionName;

    static MediaPlayer mediaPlayer; //assign memory loc else multiple audio files will play at the same time
    int position;
    Thread updateSeekBar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_play);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Bottom nav bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                releasePlayer();
                switch (item.getItemId()) {
                    case R.id.Calendar:
                        Intent intent1 = new Intent(PlayActivity.this, CalendarActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.Profile:
                        Intent intent2 = new Intent(PlayActivity.this, Profile.class);
                        startActivity(intent2);
                        break;
                    case R.id.Home:
                        Intent intent3 = new Intent(PlayActivity.this, MainActivity.class);
                        startActivity(intent3);
                        break;
                }


                return false;
            }
        });

        txtname=findViewById(R.id.txt);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Now Playing");

        btnnext=findViewById(R.id.btnnext);
        btnplay=findViewById(R.id.play_button);
        btnprev=findViewById(R.id.btnprev);

        seekbar=findViewById(R.id.seekbar);

        //thread to update the seekbar simultaneously with the progression of the audio file
        updateSeekBar=new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                //if there is still time left, update position every 500 millisec
                while(currentPosition < totalDuration){
                    try{
                        sleep(500);
                        currentPosition=mediaPlayer.getCurrentPosition();
                        seekbar.setProgress(currentPosition);
                    }
                    catch (Exception e){

                    }
                }
            }
        };


        //in case media player exists and is not released
        releasePlayer();

        Intent i = getIntent();
        Bundle bundle = i.getExtras(); //get extras from the previous activity

        mySessions = (ArrayList) bundle.getParcelableArrayList("sessions"); //names of the audio files

        sessionName = i.getStringExtra("session_name");
        txtname.setText(sessionName);
        txtname.setSelected(true);

        position = bundle.getInt("pos",0);
        int resId = getResources().getIdentifier((String) mySessions.get(position), "raw", getPackageName());

        //create and start the media player
        mediaPlayer = MediaPlayer.create(PlayActivity.this, resId);
        mediaPlayer.start();

        seekbar.setMax(mediaPlayer.getDuration()); //set the duration of seekbar
        updateSeekBar.start(); //start the thread for the seekbar
        seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.MULTIPLY);
        seekbar.getThumb().setColorFilter(getResources().getColor(R.color.design_default_color_secondary_variant), PorterDuff.Mode.MULTIPLY);

        //if click on a place of the seekbar, move to that place of the seekbar according to the audio file
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i,
                                          boolean b) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });

        btnplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                seekbar.setMax(mediaPlayer.getDuration());
                //if audio is playing then pause and show play button option
                if(mediaPlayer.isPlaying())
                {
                    btnplay.setBackgroundResource(R.drawable.ic_play);
                    try{
                    mediaPlayer.pause();}
                    catch(Exception e){}
                }
                //if audio is not playing then start it and show pause button option
                else
                {
                    btnplay.setBackgroundResource(R.drawable.ic_pause);
                    try{
                     mediaPlayer.start();}
                    catch(Exception e){}
                }
            }
        });

        //on click button next, move to the next audio file
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                //get the position of the next audio file
                position=((position+1)%mySessions.size());
                int resId = getResources().getIdentifier((String) mySessions.get(position), "raw", getPackageName());

                mediaPlayer = MediaPlayer.create(PlayActivity.this,resId);

                sessionName = mySessions.get(position).replace("_"," ");
                txtname.setText(sessionName);
                txtname.setSelected(true);

                try{
                    mediaPlayer.start();
                }catch(Exception e){}

            }
        });

        //on click button previous, move to the previous audio file
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                //get the position of the previous audio file
                position=((position-1)<0)?(mySessions.size()-1):(position-1);
                int resId = getResources().getIdentifier((String) mySessions.get(position), "raw", getPackageName());

                mediaPlayer = MediaPlayer.create(PlayActivity.this,resId);
                sessionName = mySessions.get(position).replace("_"," ");
                txtname.setText(sessionName);
                txtname.setSelected(true);

                try{
                    mediaPlayer.start();
                }catch(Exception e){}
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(PlayActivity.this, "yooyoy", Toast.LENGTH_SHORT);
        //releasePlayer();
        //super.onBackPressed();
        //finish();
        //moveTaskToBack(true);
    }

    //method to release the media player
    private void releasePlayer()
    {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer=null;
    }

    //release media player on stop activity
    @Override
    public void onStop(){
        super.onStop();
        if(!isChangingConfigurations())
            releasePlayer();
    }

}