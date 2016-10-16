package com.example.huy.managertimer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.utilities.GlideUtils;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    private final int DELAY = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = (ImageView) findViewById(R.id.ivSplash);
        GlideUtils.load(this,  R.drawable.splash,R.mipmap.ic_launcher, R.drawable.splash,imageView);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, DELAY);
    }

}
