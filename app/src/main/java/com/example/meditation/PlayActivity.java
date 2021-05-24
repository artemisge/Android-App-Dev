package com.example.meditation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.meditation.R.color.design_default_color_background;


public class PlayActivity extends AppCompatActivity {

    Button btnplay,btnnext,btnprev;
    TextView txtname;
    SeekBar seekbar;

    ArrayList<String> mySessions;
    String sname;
    public static final String EXTRA_NAME="session_name";
    static MediaPlayer mediaPlayer; //assign memory loc else multiple audio files will play at the same time
    int position;
    Thread updateSeekBar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_play);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.menu);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

        updateSeekBar=new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while(currentPosition < totalDuration){
                    try{
                        sleep(500);
                        currentPosition=mediaPlayer.getCurrentPosition();
                        seekbar.setProgress(currentPosition);
                    }
                    catch (InterruptedException e){

                    }
                }
            }
        };


        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        //String sessionName = i.getStringExtra("session name");
        //position = bundle.getInt("pos",0);

        //Uri uri = Uri.parse(mySessions.get(position).toString());
        //sname=mySessions.get(position);
        //txtname.setText(sname);
        Field[] fields = R.raw.class.getFields();
        mySessions = (ArrayList) bundle.getParcelableArrayList("sessions");
        sname=mySessions.get(position);

        String SessionName = i.getStringExtra("session_name");
        txtname.setText(SessionName);
        txtname.setSelected(true);

        position = bundle.getInt("pos",0);
        int resId = getResources().getIdentifier((String) mySessions.get(position), "raw", getPackageName());

        //txtname.setSelected(true);


        //sname= (String) arrayList.get(position);
        //txtname.setText(sname);

        mediaPlayer = MediaPlayer.create(PlayActivity.this, resId);
        mediaPlayer.start();
        seekbar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.design_default_color_secondary), PorterDuff.Mode.MULTIPLY);
        seekbar.getThumb().setColorFilter(getResources().getColor(R.color.design_default_color_primary), PorterDuff.Mode.MULTIPLY);

        seekbar.setOnSeekBarChangeListener(new
                                              SeekBar.OnSeekBarChangeListener() {
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
                if(mediaPlayer.isPlaying())
                {
                    btnplay.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else
                {
                    btnplay.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position+1)%mySessions.size());
                int resId = getResources().getIdentifier((String) mySessions.get(position), "raw", getPackageName());
                // songNameText.setText(getSongName);
                mediaPlayer = MediaPlayer.create(PlayActivity.this,resId);

                sname = mySessions.get(position);
                txtname.setText(sname);

                try{
                    mediaPlayer.start();
                }catch(Exception e){}

            }
        });

        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //songNameText.setText(getSongName);
                mediaPlayer.stop();
                mediaPlayer.release();

                position=((position-1)<0)?(mySessions.size()-1):(position-1);
                int resId = getResources().getIdentifier((String) mySessions.get(position), "raw", getPackageName());
                mediaPlayer = MediaPlayer.create(PlayActivity.this,resId);
                sname = mySessions.get(position);
                txtname.setText(sname);
                mediaPlayer.start();
            }
        });

    }


}