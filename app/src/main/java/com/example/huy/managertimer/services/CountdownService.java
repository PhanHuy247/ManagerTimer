package com.example.huy.managertimer.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.activity.SettingActivity;
import com.example.huy.managertimer.fragment.ClockFragment;

public class CountdownService extends Service {
    public CountdownService() {
    }
    public CountDownTimer mCountdownTimer;
    public long millisLeft;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int timeInMin = intent.getIntExtra("timeInMin", 25);
        long timeInMilliSec = timeInMin*60000;
        setUpCountdownTimer(timeInMilliSec);
        return START_NOT_STICKY;
    }

    public void setUpCountdownTimer(long time) {
        mCountdownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                int min = (int) millisUntilFinished/(60000);
                int sec = (int) ((millisUntilFinished%(60000))/1000);
                ClockFragment.tv_countdown.setText(min+" : "+sec);
                millisLeft = millisUntilFinished;
                ClockFragment.imb_pause.setImageResource(R.drawable.ic_pause_black_24dp);
            }

            public void onFinish() {
                ClockFragment.tv_countdown.setText("Done");
                stopSelf();
            }
        }.start();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Service was terminated!", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    public class MyBinder extends Binder {
        public CountdownService getService(){
            return CountdownService.this;
        }
    }
}
