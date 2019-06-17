
package com.flourishtimer;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emredavarci.circleprogressbar.CircleProgressBar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import br.com.bloder.magic.view.MagicButton;


public class TimerActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    Notification not;
    TextView get,timer;
    CountDownTimer processtime,lbtime,sbtime;
    int sbint,lbint;
    int process;
    CircleProgressBar progressBars;
    MagicButton cancel,done;
    AdView mAdView;

    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flourishtimer.R.layout.activity_timer);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-5040215542785098/8482773606");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener()
                                     {

                                         @Override
                                         public void onAdClosed() {
                                             super.onAdClosed();
                                             startActivity(new Intent(TimerActivity.this,MainActivity.class));
                                             interstitialAd.loadAd(new AdRequest.Builder().build());
                                         }
                                     }
        );

        MobileAds.initialize(this,"ca-app-pub-5040215542785098~6471015481");
        mAdView = findViewById(com.flourishtimer.R.id.adView);
        AdRequest ad = new AdRequest.Builder().build();
        mAdView.loadAd(ad);

        timer = findViewById(com.flourishtimer.R.id.timer);
        notificationManager = NotificationManagerCompat.from(this);
        get = findViewById(com.flourishtimer.R.id.get);

        cancel = findViewById(com.flourishtimer.R.id.cancel);
        done = findViewById(com.flourishtimer.R.id.done);


        Intent i = getIntent();
        get.setText("" + i.getStringExtra("taskname"));
        process = i.getIntExtra("processint",0);
        sbint = i.getIntExtra("shortBreak",1);
        lbint = i.getIntExtra("longBreak",2);

         progressBars = (CircleProgressBar) findViewById(com.flourishtimer.R.id.progressBar);
        progressBars.setProgress(process);


        initialtimer();

        done.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimerActivity.this, MainActivity.class);
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else {
                    startActivity(i);
                }
                notificationManager.cancelAll();
            }
        });

        cancel.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Activity) TimerActivity.this).isFinishing()) {
                    //show dialog
                    new FancyGifDialog.Builder(TimerActivity.this)
                            .setTitle("Are you sure?")
                            .setGifResource(com.flourishtimer.R.drawable.appicon)
                            .setMessage("Do you wanna give up?")
                            .setNegativeBtnText("Yes! I do")
                            .setPositiveBtnBackground("#228B22")
                            .setPositiveBtnText("No! Let me try")
                            .setNegativeBtnBackground("#FF0000")
                            .isCancellable(false)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    notificationManager.cancelAll();
                                    Intent i = new Intent(TimerActivity.this, MainActivity.class);
                                    if(interstitialAd.isLoaded()){
                                        interstitialAd.show();
                                    }else {
                                        startActivity(i);
                                    }
                                }
                            })
                            .build();
                }
            }
        });


    }

    public void updateTimer(int secondsLeft){

        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if(seconds<=9){
            secondString = "0"+secondString;
        }
        timer.setText(Integer.toString(minutes) + ":"  + secondString);
    }

    public void initialtimer(){
        sendNotif();
        progressBars.setMaxValue(3600);
        processtime = new CountDownTimer(process * 1000 + 100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished/1000);
            }
            @Override
            public void onFinish() {
            if (!((Activity) TimerActivity.this).isFinishing()) {
                new FancyGifDialog.Builder(TimerActivity.this)
                        .setTitle("Well Done!")
                        .setGifResource(com.flourishtimer.R.drawable.appicon)
                        .setMessage("A break is necessary to keep yourself going..So TAKE A BREAK!")
                        .setNegativeBtnText("Long Break!")
                        .setPositiveBtnBackground("#228B22")
                        .setPositiveBtnText("Short Break!")
                        .setNegativeBtnBackground("#FF0000")
                        .isCancellable(false)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                shortBreak();
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                longBreak();
                            }
                        })
                        .build();

                }
            }
        }.start();
    }

    public void shortBreak(){
        progressBars.setMaxValue(600);
        sbtime = new CountDownTimer(sbint * 1000 + 100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                if (!((Activity) TimerActivity.this).isFinishing()) {
                    //show dialog
                    new FancyGifDialog.Builder(TimerActivity.this)
                            .setTitle("Back to work")
                            .setMessage("Well done! Its time to get back to work again")
                            .setNegativeBtnText("Cancel")
                            .setGifResource(com.flourishtimer.R.drawable.appicon)
                            .setPositiveBtnBackground("#228B22")
                            .setPositiveBtnText("Start Timer")
                            .setNegativeBtnBackground("#FF0000")
                            .isCancellable(false)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    initialtimer();
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    sbtime.cancel();
                                    notificationManager.cancelAll();
                                    Intent i = new Intent(TimerActivity.this, MainActivity.class);
                                    if(interstitialAd.isLoaded()){
                                        interstitialAd.show();
                                    }else {
                                        startActivity(i);
                                    }
                                }
                            })
                            .build();
                }
            }
        }.start();
    }

    public void longBreak(){
        progressBars.setMaxValue(1800);
        lbtime = new CountDownTimer(lbint * 1000 + 100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                if (!((Activity) TimerActivity.this).isFinishing()) {
                    //show dialog
                    new FancyGifDialog.Builder(TimerActivity.this)
                            .setTitle("Back to work")
                            .setMessage("Well done! Its time to get back to work again")
                            .setNegativeBtnText("Cancel")
                            .setGifResource(com.flourishtimer.R.drawable.appicon)
                            .setPositiveBtnBackground("#228B22")
                            .setPositiveBtnText("Start Timer")
                            .setNegativeBtnBackground("#FF0000")
                            .isCancellable(false)
                            .OnPositiveClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    initialtimer();
                                }
                            })
                            .OnNegativeClicked(new FancyGifDialogListener() {
                                @Override
                                public void OnClick() {
                                    lbtime.cancel();
                                    notificationManager.cancelAll();
                                    Intent i = new Intent(TimerActivity.this, MainActivity.class);
                                    if(interstitialAd.isLoaded()){
                                        interstitialAd.show();
                                    }else {
                                        startActivity(i);
                                    }
                                }
                            })
                            .build();
                }
            }
        }.start();
    }



    public void sendNotif(){


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);


        not = new NotificationCompat.Builder(this,Notif.CHANNEL_ID)
                .setSmallIcon(com.flourishtimer.R.drawable.appicon)
                .setContentTitle("Do Your Task!")
                .setContentText("Your Timer is Running")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setOngoing(false)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(1,not);

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(1);
    }


}
