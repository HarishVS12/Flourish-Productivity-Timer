package com.flourishtimer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notif extends Application {

    public static final String CHANNEL_ID = "Channel1";
    @Override
    public void onCreate() {
        super.onCreate();
        createnotif();
    }


    private void createnotif(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("To remind the user that the timer is running in the app.");
            channel1.enableLights(true);

            NotificationManager man = getSystemService(NotificationManager.class);
            man.createNotificationChannel(channel1);
        }
    }
}
