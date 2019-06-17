package com.flourishtimer;

import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import br.com.bloder.magic.view.MagicButton;


public class MainActivity extends AppCompatActivity {

    EditText taskText;
    IndicatorSeekBar process,longBreak,shortBreak;
    TextView sbtext,protext,lbtext;
    MagicButton mg;
    int processint,lbint,sbint;
    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flourishtimer.R.layout.activity_main);

        MobileAds.initialize(this,"ca-app-pub-5040215542785098~6471015481");
        mAdView = findViewById(com.flourishtimer.R.id.adView);
        AdRequest ad = new AdRequest.Builder().build();
        mAdView.loadAd(ad);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, MyIntro.class);

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();



        taskText = findViewById(com.flourishtimer.R.id.taskText);
        process = findViewById(com.flourishtimer.R.id.processtimeseek);
        longBreak = findViewById(com.flourishtimer.R.id.longbreakseek);
        shortBreak = findViewById(com.flourishtimer.R.id.shortbreakseek);
        mg = findViewById(com.flourishtimer.R.id.magic_button);


        process.setMax(3600);
        process.setProgress(940);

        longBreak.setMax(1800);
        longBreak.setProgress(650);

        shortBreak.setMax(600);
        shortBreak.setProgress(300);



        mg.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processint = process.getProgress();
                sbint = shortBreak.getProgress();
                lbint = longBreak.getProgress();
                if(taskText.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Task is Empty! Fill the Task",Toast.LENGTH_LONG).show();
                }
                else{
                    String task = taskText.getText().toString();
                    Intent in = new Intent(MainActivity.this, TimerActivity.class);
                    in.putExtra("taskname",task);
                    in.putExtra("processint",processint);
                    in.putExtra("shortBreak",sbint);
                    in.putExtra("longBreak",lbint);
                    startActivity(in);
                }
            }
        });

        process.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
//                int min = seekParams.progress/60;
//                int sec = min - seekParams.progress;
//                String concat = Integer.toString(min)+Integer.toString(sec);
//                seekParams.progress = Integer.parseInt(concat);
                updateTimer(seekParams.progress,process);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        longBreak.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
//                int min = seekParams.progress/60;
//                int sec = min - seekParams.progress;
//                String concat = Integer.toString(min)+Integer.toString(sec);
//                seekParams.progress = Integer.parseInt(concat);
                updateTimer(seekParams.progress,longBreak);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        shortBreak.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
//                int min = seekParams.progress/60;
//                int sec = min - seekParams.progress;
//                String concat = Integer.toString(min)+Integer.toString(sec);
//                seekParams.progress = Integer.parseInt(concat);
                updateTimer(seekParams.progress,shortBreak);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

    }
    public void updateTimer(int secondsLeft,IndicatorSeekBar seekBar) {

        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }
        seekBar.setIndicatorTextFormat(minutes + " mts : "+secondString + " sec | " + "${PROGRESS} sec");

    }

    @Override
    public void onBackPressed() {
    }

    public void binfo(View view){
        Intent i = new Intent(MainActivity.this,InfoAct.class);
        startActivity(i);
    }

    public void help(View view){
        Intent i =new Intent(MainActivity.this,MyIntro.class);
        startActivity(i);
    }
}
