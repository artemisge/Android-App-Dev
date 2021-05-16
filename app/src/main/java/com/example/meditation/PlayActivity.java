package com.example.meditation;

import android.content.Intent;
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

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    Button btnplay,btnnext,btnprev;
    TextView txtname,txtstart,txtstop;
    SeekBar seekmusic;
    BarVisualizer visualizer;

    String sname;
    public static final String EXTRA_NAME="session_name";
    static MediaPlayer mediaPlayer;
    int position;


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
                        Intent intent1 = new Intent(PlayActivity.this, Calendar.class);
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

        btnnext=findViewById(R.id.btnnext);
        btnplay=findViewById(R.id.play_button);
        btnprev=findViewById(R.id.btnprev);
        txtname=findViewById(R.id.txt);
        txtstart=findViewById(R.id.txtstart);
        txtstop=findViewById(R.id.txtstop);
        seekmusic=findViewById(R.id.seekbar);
        visualizer=findViewById(R.id.blast);

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

        ArrayList arrayList = new ArrayList<String>();
        Field[] fields = R.raw.class.getFields();
        for(int j=0; j<fields.length; j++){
            arrayList.add(fields[j].getName());
        }
        position = bundle.getInt("pos",0);

        //txtname.setSelected(true);
        int resId = getResources().getIdentifier((String) arrayList.get(position), "raw", getPackageName());

        //sname= (String) arrayList.get(position);
        //txtname.setText(sname);

        mediaPlayer = MediaPlayer.create(PlayActivity.this, resId);
        mediaPlayer.start();

        btnplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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

    }
}