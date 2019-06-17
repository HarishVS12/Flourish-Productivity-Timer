package com.flourishtimer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MyIntro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        addSlide(firstFragment);
//        addSlide(secondFragment);
//        addSlide(thirdFragment);
//        addSlide(fourthFragment);

        addSlide(AppIntroFragment.newInstance("CHOOSE A TASK",
                "Choose and define a task to be accompolished. BIG or small.",
                com.flourishtimer.R.drawable.list,
                Color.parseColor("#F7760B")));
        addSlide(AppIntroFragment.newInstance("SET THE TIMER",
                "Set the Timer to a duration. BELIEVE IN YOURSELF that you could give your undivided attention to the task that you are about to do.",
                com.flourishtimer.R.drawable.countd,
                Color.parseColor("#FF0022")));
        addSlide(AppIntroFragment.newInstance("WORK ON THE TASK!",
                "Work on the task until the Timer gets over and STRICTLY NO PHONE!!",
                com.flourishtimer.R.drawable.tools,
                Color.parseColor("#2600FF")));
        addSlide(AppIntroFragment.newInstance("TAKE A BREAK!",
                "TAKE A SHORT BREAK! Relax yourself and Repeat the task 4 times until you take a LONG BREAK! Enjoy Being Productive!!",
                com.flourishtimer.R.drawable.breaks,
                Color.parseColor("#8E26D2")));

        showStatusBar(false);
        showSkipButton(false);


        // Instead of fragments, you can also use our default slide.
        // Just create a `SliderPage` and provide title, description, background and image.
        // AppIntro will do the rest.
//        SliderPage sliderPage = new SliderPage();
//        sliderPage.setTitle("sad");
//        sliderPage.setDescription("ada");
//        sliderPage.setImageDrawable(R.drawable.appicon);
//        sliderPage.setBgColor(Color.parseColor("#fff234"));
//        addSlide(AppIntroFragment.newInstance(sliderPage));

        // OPTIONAL METHODS
        // Override bar/separator color.
//        setBarColor(Color.parseColor("#3F51B5"));
//        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
//        showSkipButton(true);
//        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
    }

}
