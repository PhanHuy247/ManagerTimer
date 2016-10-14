package com.example.huy.managertimer.services;

import android.annotation.TargetApi;
import android.app.Notification;
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
import android.util.Log;
import android.widget.Toast;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;
import com.example.huy.managertimer.activity.MainActivity;
import com.example.huy.managertimer.activity.SettingActivity;
import com.example.huy.managertimer.fragment.ClockFragment;
import com.example.huy.managertimer.fragment.TaskFragment;
import com.google.gson.Gson;

public class CountdownService extends Service {
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";
    public static final int NOTIFY_ID = 1912;
    private NotiReceiver receiver;

    public CountdownService() {
    }
    public CountDownTimer mCountdownTimer;
    public long millisLeft;
    public static final String ACTION_FINISH = "done";
    private Bitmap artwork;
    Intent resultIntent;
    PendingIntent pendInt;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setBroadcastReceiver();
        int timeInMin = intent.getIntExtra("timeInMin", 25);
        long timeInMilliSec = timeInMin*60000;
        setUpCountdownTimer(timeInMilliSec);
        return START_NOT_STICKY;
    }
    private void setBroadcastReceiver() {
        receiver = new NotiReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_STOP);
        registerReceiver(receiver, filter);

    }
    public void setUpCountdownTimer(final long time) {
        artwork = BitmapFactory.decodeResource(getResources(), R.drawable.brain_power);



        resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        pendInt = PendingIntent.getActivity(getApplicationContext(), 0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        setupNotification();
        mCountdownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                int min = (int) (millisUntilFinished/60000);
                int sec = (int) ((millisUntilFinished%60000)/1000);
                String text = min+" : "+sec;
                ClockFragment.tv_countdown.setText(text);
                millisLeft = millisUntilFinished;
                ClockFragment.imb_pause.setImageResource(R.drawable.ic_pause_black_24dp);
                ClockFragment.curCountdownStr = text;

            }

            public void onFinish() {

                if (ClockFragment.isWorking){
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

                }
                ClockFragment.hasNext = true;
                Intent intent = new Intent(ACTION_FINISH);
                sendBroadcast(intent);
                ClockFragment.isOnSess = false;

            }
        }.start();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupNotification () {
        Notification.Builder mBuilder = new Notification.Builder(CountdownService.this);
        int id;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (mediaPlayer.isPlaying()){
//                id = R.drawable.ic_pause_white_36dp;
//            }
//            else {
//                id = R.drawable.ic_play_arrow_white_36dp;
//            }
            mBuilder.setContentIntent(pendInt)
                    .setColor(0x388E3C)
                    .setLargeIcon(artwork)
                    .setSmallIcon(R.drawable.brain_power)
                    .setShowWhen(false)
                    //                .setContentTitle("New Message")
                    //                .setContentText("You've received new message.")
                    //                .setTicker("New Message Alert!")
                    .setStyle(new Notification.MediaStyle()
                            // Attach our MediaSession token
//                            .setMediaSession(mediaSession.getSessionToken())
                            // Show our playback controls in the compat view
                            .setShowActionsInCompactView(0, 1, 2))
//                    .setContentText(songs.get(currentPos).getArtist())
//                    .setContentTitle(songs.get(currentPos).getTitle())
                    // Add some playback controls
                    .setPriority(Notification.PRIORITY_MAX)
//                    .setTicker("Playing: "+songs.get(currentPos).getTitle()+" - "+ songs.get(currentPos).getArtist())
                    .setContentIntent(pendInt)

//                    .addAction(R.drawable.ic_skip_previous_white_36dp, "prev", retreivePlaybackAction(3))
                    .addAction(R.drawable.ic_pause_black_24dp, "pause", retreivePlaybackAction(1))
                    .addAction(R.drawable.ic_skip_next_black_24dp, "next", retreivePlaybackAction(2))
                    .addAction(R.drawable.ic_stop_black_24dp, "stop", retreivePlaybackAction(4));

        }
        else {
//            if (mediaPlayer.isPlaying()){
//                id = R.drawable.ic_pause_green_800_36dp;
//            }
//            else {
//                id = R.drawable.ic_play_arrow_green_800_36dp;
//            }
            mBuilder.setContentIntent(pendInt)
//                .setLargeIcon(artwork)
                    .setSmallIcon(R.drawable.brain_power)
                    .setShowWhen(false)
//                    .setSubText("just check")
//                    .setContentText(songs.get(currentPos).getArtist())
//                    .setContentTitle(songs.get(currentPos).getTitle())
                    .setContentIntent(pendInt)
//                    .setTicker("Playing: "+songs.get(currentPos).getTitle()+" - "+ songs.get(currentPos).getArtist())
                    .setPriority(Notification.PRIORITY_MAX)
                    .addAction(R.drawable.ic_pause_black_24dp, "pause", retreivePlaybackAction(1))
                    .addAction(R.drawable.ic_skip_next_black_24dp, "next", retreivePlaybackAction(2))
                    .addAction(R.drawable.ic_stop_black_24dp, "stop", retreivePlaybackAction(4));
        }
        Notification not = null;
        not = mBuilder.build();
        startForeground(NOTIFY_ID, not);
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
        Toast.makeText(getApplicationContext(), "Service was terminated!", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.tasks_infos), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        for (Task task:TaskFragment.tasks){
            String json = gson.toJson(task); // myObject - instance of MyObject
            prefsEditor.putString(task.getTitle(), json);
        }
        String json = gson.toJson(TaskFragment.defaultTask);
        prefsEditor.putString(getString(R.string.defaultTask), json);
        prefsEditor.commit();
        Log.d("saveData", TaskFragment.defaultTask.getWTime()+"");
        for (int i = 0; i < TaskFragment.tasks.size(); i++) {
            Log.d("saveData"+(i+1), TaskFragment.tasks.get(i).getWTime()+"");
        }
        super.onDestroy();
    }
    private class NotiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_NEXT)){


            }
            else if (intent.getAction().equals(ACTION_PAUSE)){



            }
            else if (intent.getAction().equals(ACTION_STOP)){
                mCountdownTimer.cancel();
                stopForeground(true);
                ClockFragment.isOnSess = false;
                ClockFragment.isWorking = false;
                ClockFragment.isCounting = false;
                ClockFragment.hasNext = false;
                stopSelf();
            }

        }
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
