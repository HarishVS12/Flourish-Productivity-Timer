package com.flourishtimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashActivity extends AppCompatActivity {

    private ImageView im;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.flourishtimer.R.layout.splashscreen);


        //  Declare a new thread to do a preference check





        im = findViewById(com.flourishtimer.R.id.imageView2);
        tv = findViewById(com.flourishtimer.R.id.textView);
        Animation myanim = AnimationUtils.loadAnimation(this, com.flourishtimer.R.anim.anim);
        myanim.setDuration(2800);
        im.startAnimation(myanim);
        Animation textanim = AnimationUtils.loadAnimation(this, com.flourishtimer.R.anim.anim);
        textanim.setDuration(2750);
        tv.startAnimation(textanim);


        final Intent i = new Intent(this,MainActivity.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();




//public class MyIntro extends AppIntro {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        addSlide(AppIntroFragment.newInstance("Harish",
//                "This is Harish",
//                R.drawable.appicon,
//                Color.parseColor("#09FA54")));
//        addSlide(AppIntroFragment.newInstance("Harish",
//                "This is Harish",
//                R.drawable.appicon,
//                Color.parseColor("#FF0022")));
//
//        showStatusBar(false);
//        setBarColor(Color.parseColor("#AC5623"));
//        setSeparatorColor(Color.parseColor("#ffffff"));
//    }
//
//    @Override
//    public void onDonePressed() {
//        super.onDonePressed();
//        startActivity(new Intent(this,MainActivity.class));
//    }
//
//    @Override
//    public void onSkipPressed(Fragment currentFragment) {
//        super.onSkipPressed(currentFragment);
//        Toast.makeText(this,"Skip",Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onSlideChanged() {
//        super.onSlideChanged();
//        Toast.makeText(this,"Slide Changed",Toast.LENGTH_LONG).show();
//
//    }
//}



    }
}
