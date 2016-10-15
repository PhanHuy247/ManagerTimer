package com.example.huy.managertimer.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.huy.managertimer.HelperClass;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;
import com.example.huy.managertimer.activity.MainActivity;
import com.example.huy.managertimer.activity.SettingActivity;
import com.example.huy.managertimer.fragment.ClockFragment;
import com.example.huy.managertimer.fragment.TaskFragment;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.example.huy.managertimer.fragment.ClockFragment.hasNext;
import static com.example.huy.managertimer.fragment.ClockFragment.isCounting;
import static com.example.huy.managertimer.fragment.ClockFragment.isOnSess;
import static com.example.huy.managertimer.fragment.ClockFragment.isWorking;
import static com.example.huy.managertimer.fragment.ClockFragment.position;

public class CountdownService extends Service {
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";
    public static final int NOTIFY_ID = 1912;
//    private NotiReceiver receiver;

    public CountdownService() {
    }
    public CountDownTimer mCountdownTimer;
    public long millisLeft;
    public static final String ACTION_FINISH = "done";
    private Bitmap artwork;
    Intent resultIntent;
    PendingIntent pendInt;
    int min, sec;
    String title;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        setBroadcastReceiver();
        int timeInMin = intent.getIntExtra("timeInMin", 25);
        long timeInMilliSec = timeInMin*60000;
        setUpCountdownTimer(timeInMilliSec);
        return START_NOT_STICKY;
    }
//    private void setBroadcastReceiver() {
//        receiver = new NotiReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ACTION_NEXT);
//        filter.addAction(ACTION_PAUSE);
//        filter.addAction(ACTION_PREVIOUS);
//        filter.addAction(ACTION_STOP);
//        registerReceiver(receiver, filter);
//
//    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setUpCountdownTimer(final long time) {
        artwork = BitmapFactory.decodeResource(getResources(), R.drawable.brain_power);



        resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        pendInt = PendingIntent.getActivity(getApplicationContext(), 0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification.Builder mBuilder = setupNotification();

        mCountdownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                 min = (int) (millisUntilFinished/60000);
                 sec = (int) ((millisUntilFinished%60000)/1000);
                String text = min+" : "+sec;
                ClockFragment.tv_countdown.setText(text);
                millisLeft = millisUntilFinished;
                ClockFragment.imb_pause.setImageResource(R.drawable.ic_pause_black_24dp);
                ClockFragment.curCountdownStr = text;
                mBuilder.setContentText(min+" : "+(sec-1)+" - "+title);
                Notification not = mBuilder.build();
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFY_ID, not);
//                setupNotification();
            }

            public void onFinish() {

                if (isWorking){
                    if (ClockFragment.position!=-1){
                        int wTime = TaskFragment.tasks.get(ClockFragment.position).getWTime()+(int) (time/60000);
                        TaskFragment.tasks.get(ClockFragment.position).setWTime(wTime);
                        Log.d("workTime", wTime+"");
                    }
                    else {
                        int wTime = TaskFragment.defaultTask.getWTime() + (int) (time/60000);
                        TaskFragment.defaultTask.setWTime(wTime);
                        Log.d("workTime", wTime+"");
                    }
                    saveDateStat(time);

                }

                Intent intent = new Intent(ACTION_FINISH);
                sendBroadcast(intent);



            }
        }.start();
    }

    private void saveDateStat(long time) {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat1));
        Date date = new Date();
        SharedPreferences datePrefs = getSharedPreferences(dateFormat.format(date), MODE_PRIVATE);
        SharedPreferences.Editor editor = datePrefs.edit();
        Set<String> set = datePrefs.getStringSet(getString(R.string.setTaskTitle), new HashSet<String>());

        set.add(title);
        editor.putStringSet(getString(R.string.setTaskTitle), set);
        int wTime = datePrefs.getInt(title, 0);
        wTime += (int) time/60000;
        editor.putInt(title, wTime);
        editor.commit();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Notification.Builder setupNotification () {
        Notification.Builder mBuilder = new Notification.Builder(CountdownService.this);
        int pos = ClockFragment.position;
        String text;
        if (pos==-1){
            title = "defaultTask";
        }
        else {
            title = TaskFragment.tasks.get(pos).getTitle();
        }

        int pauseID;
        if (isOnSess){
            if (isCounting){
                pauseID = R.drawable.ic_pause_black_24dp;
                text = "pause";
            }
            else {
                pauseID = R.drawable.ic_play_arrow_black_24dp;
            }
        }
        else {
            if (isWorking){
                pauseID = R.drawable.ic_airline_seat_flat_black_24dp;
            }
            else {
                pauseID = R.drawable.ic_airline_seat_recline_normal_black_24dp;
            }
        }

        mBuilder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.ic_timelapse_black_24dp)
                .setShowWhen(false)

                .setContentText(min+" : "+((sec-1)>0?sec-1:0)+" - "+title)
                .setContentTitle(getString(R.string.app_name))

                // Add some playback controls
                .setPriority(Notification.PRIORITY_MAX)
                .setTicker("Doing "+title)
                .setContentIntent(pendInt)

//                    .addAction(R.drawable.ic_skip_previous_white_36dp, "prev", retreivePlaybackAction(3))
                .addAction(pauseID, "pause", retreivePlaybackAction(1))
                .addAction(R.drawable.ic_skip_next_black_24dp, "next", retreivePlaybackAction(2))
                .addAction(R.drawable.ic_stop_black_24dp, "stop", retreivePlaybackAction(4));

        Notification not = mBuilder.build();;
        startForeground(NOTIFY_ID, not);
        return mBuilder;
    }
    private PendingIntent retreivePlaybackAction(int which) {
        Intent action;
        PendingIntent pendingIntent;
        switch (which) {
            case 1:
                // Play and pause
                action = new Intent(ACTION_PAUSE);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, action, 0);
                return pendingIntent;
            case 2:
                // Skip tracks
                action = new Intent(ACTION_NEXT);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, action, 0);
                return pendingIntent;
//            case 3:
//                // Previous tracks
//                action = new Intent(ACTION_PREVIOUS);
//                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, action, 0);
//                return pendingIntent;
            case 4:
                //stop foreground
                action = new Intent(ACTION_STOP);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 4, action, 0);
                return pendingIntent;
            default:
                break;
        }
        return null;
    }
    @Override
    public void onDestroy() {
//        unregisterReceiver(receiver);
        super.onDestroy();
    }
//    private class NotiReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(ACTION_NEXT)){
//                setupNotification();
//
//
//            }
//            else if (intent.getAction().equals(ACTION_PAUSE)){
//                mCountdownTimer.cancel();
//                if (isCounting = true){
//                    isCounting = false;
//                    setupNotification();
//                }
//                else {
//                    isCounting = true;
//                    setupNotification();
//                }
//
//            }
//            else if (intent.getAction().equals(ACTION_STOP)){
////                mCountdownTimer.cancel();
////                stopForeground(true);
////                isOnSess = false;
////                isWorking = false;
////                isCounting = false;
////                hasNext = false;
////                HelperClass.saveTasks(getApplicationContext());
////                stopSelf();
//            }
//
//        }
//    }

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
