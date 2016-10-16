package com.example.huy.managertimer.fragment;


import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.constant.Constant;
import com.example.huy.managertimer.databases.HelperClass;
import com.example.huy.managertimer.services.CountdownService;
import com.example.huy.managertimer.utilities.GlideUtils;
import com.example.huy.managertimer.utilities.PreferenceUtils;

import java.util.Calendar;

import at.grabner.circleprogress.CircleProgressView;

import static com.example.huy.managertimer.activity.SettingActivity.bTime;
import static com.example.huy.managertimer.activity.SettingActivity.lBTime;
import static com.example.huy.managertimer.activity.SettingActivity.lBTimeMode;
import static com.example.huy.managertimer.activity.SettingActivity.nOSess;
import static com.example.huy.managertimer.activity.SettingActivity.shakeMode;
import static com.example.huy.managertimer.activity.SettingActivity.silenceMode;
import static com.example.huy.managertimer.activity.SettingActivity.soundMode;
import static com.example.huy.managertimer.activity.SettingActivity.vol;
import static com.example.huy.managertimer.activity.SettingActivity.wTime;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment implements View.OnClickListener {
    public static String TIME_IN_MIN = "timeInMin";
    public static String POSITION = "position";
    public static CountdownService mService = null;
    public static boolean isWorking;
    public static TextView tv_countdown;
    ImageButton imb_start, imb_skipNext, imb_break, imb_stop;
    TextView tv_taskTitle;
    public static CircleProgressView countdownClock;
    public static ImageButton imb_pause;
    public static boolean isCounting;
    public static boolean isOnSess;
    public static boolean hasNext;
    public static int position = -1;
    private boolean isBound = false;
    ServiceConnection connection;
    public static String curCountdownStr;
    private MyBroadcastReceiver broadcastReceiver;
    ImageView ivClock;
    FragmentManager fm;

    public ClockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clock, container, false);


        setBroadcastReceiver();
        initView(view);
        setOnClick();
        return view;
    }

    private void resetCoundownTimer(int position) {
        tv_taskTitle.setText(TaskFragment.taskItems.get(position).getTitle());
        imb_start.setVisibility(View.GONE);
        imb_break.setVisibility(View.GONE);
        imb_skipNext.setVisibility(View.VISIBLE);
        imb_pause.setVisibility(View.VISIBLE);
        imb_stop.setVisibility(View.VISIBLE);
        tv_countdown.setText(wTime + getResources().getString(R.string.time_0));
        isOnSess = true;
        isWorking = true;
        isCounting = true;
        refreshService();


    }

    @Override
    public void onStart() {
        countdownClock.setMaxValue(PreferenceUtils.getValue(getActivity(), Constant.MAX_CIRCLE, 0));

        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        GlideUtils.loadCircleImage(getActivity(), "https://migreat.files.wordpress.com/2014/07/man-with-laptop-sitting-and-thinking-baloon-in-background-flickr-nic519.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
        countdownClock.setMaxValue(PreferenceUtils.getValue(getActivity(), Constant.MAX_CIRCLE, 0));
        if (PreferenceUtils.getValue(getActivity(), Constant.TASK_SEND, false)) {

            if (isCounting) {
                Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.tiep_tuc_hien_tai), Toast.LENGTH_LONG).show();
            } else {
                if (wTime != 0) {
                    isCounting = true;
                    isWorking = true;
                    setUpService();
                    imb_start.setVisibility(View.GONE);
                    imb_break.setVisibility(View.GONE);
                    imb_skipNext.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.VISIBLE);
                    imb_stop.setVisibility(View.VISIBLE);

                    countdownClock.setMaxValue(wTime * 60);
//                    Log.d("phanhuy",getActivity().getIntent().getExtras().getInt(Constant.TASK_FRAGMENT_WORK, 0)+"");
                    //     wTime = wTime - getActivity().getIntent().getExtras().getInt(Constant.TASK_FRAGMENT_WORK, 0);
                    countdownClock.setValue(wTime * 60);
                    PreferenceUtils.setValue(getActivity(), Constant.MAX_CIRCLE, wTime * 60);
                    countdownClock.setRimColor(getResources().getColor(R.color.backgroud_circle));
                    GlideUtils.loadCircleImage(getActivity(), "https://thumbs.dreamstime.com/z/happy-businessman-work-below-black-clouds-white-background-35044869.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
                    PreferenceUtils.setValue(getActivity(), Constant.TASK_SEND, false);

                }
            }

        }
    }

    private void refreshService() {
        if (mService != null) {
            mService.mCountdownTimer.cancel();
            mService.stopSelf();

        }
        setUpService();
    }

    private void setBroadcastReceiver() {
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(mService.ACTION_FINISH);
        filter.addAction(mService.ACTION_STOP);
        filter.addAction(mService.ACTION_NEXT);
        filter.addAction(mService.ACTION_PAUSE);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    private void setOnClick() {
        imb_start.setOnClickListener(this);
        imb_skipNext.setOnClickListener(this);
        imb_pause.setOnClickListener(this);
        imb_break.setOnClickListener(this);
        imb_stop.setOnClickListener(this);
    }

    private void initView(View view) {
        tv_countdown = (TextView) view.findViewById(R.id.tv_countDown);
        imb_start = (ImageButton) view.findViewById(R.id.imb_startCD);
        imb_skipNext = (ImageButton) view.findViewById(R.id.imb_skip);
        imb_pause = (ImageButton) view.findViewById(R.id.imb_pause);
        imb_break = (ImageButton) view.findViewById(R.id.imb_break);
        imb_stop = (ImageButton) view.findViewById(R.id.imb_stop);
        tv_taskTitle = (TextView) view.findViewById(R.id.tv_taskTitle);
        countdownClock = (CircleProgressView) view.findViewById(R.id.count_down_clock);
        ivClock = (ImageView) view.findViewById(R.id.ivClock);
        countdownClock.setMaxValue(PreferenceUtils.getValue(getActivity(), Constant.MAX_CIRCLE, 0));
        GlideUtils.loadCircleImage(getActivity(), "https://migreat.files.wordpress.com/2014/07/man-with-laptop-sitting-and-thinking-baloon-in-background-flickr-nic519.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
        if (imb_skipNext.getVisibility() == View.INVISIBLE || imb_skipNext.getVisibility() == View.GONE) {
            countdownClock.setRimColor(getResources().getColor(R.color.colorPrimary));
        }
        if (imb_skipNext.getVisibility() == View.VISIBLE)
            countdownClock.setRimColor(getResources().getColor(R.color.backgroud_circle));
        if (!isCounting) {
            imb_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        }
        getPreferencesData();
        int temp = getActivity().getIntent().getIntExtra(POSITION, -1);
        if (position != temp && temp != -1) {
            position = temp;
            resetCoundownTimer(position);
        } else {
            String tmp;
            if (position == -1) {
                tmp = "";

            } else {
                tmp = TaskFragment.taskItems.get(position).getTitle();
            }
            tv_taskTitle.setText(tmp);

        }
        if (isOnSess) {
            tv_countdown.setText(curCountdownStr);
            imb_start.setVisibility(View.GONE);
            imb_break.setVisibility(View.GONE);
            imb_skipNext.setVisibility(View.VISIBLE);
            imb_pause.setVisibility(View.VISIBLE);
            imb_stop.setVisibility(View.VISIBLE);

        } else {
            if (hasNext) {
                if (isWorking) {
                    tv_countdown.setText(bTime + getResources().getString(R.string.time_0));
                    imb_start.setVisibility(View.GONE);
                    imb_break.setVisibility(View.VISIBLE);
                    imb_skipNext.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                } else {
                    tv_countdown.setText(wTime + getResources().getString(R.string.time_0));
                    imb_start.setVisibility(View.VISIBLE);
                    imb_break.setVisibility(View.GONE);
                    imb_skipNext.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                }
            } else {
                tv_countdown.setText(wTime + getResources().getString(R.string.time_0));
                imb_start.setVisibility(View.VISIBLE);
                imb_break.setVisibility(View.VISIBLE);
                imb_skipNext.setVisibility(View.GONE);
                imb_pause.setVisibility(View.GONE);
                imb_stop.setVisibility(View.GONE);
            }
            countdownClock.setMaxValue(PreferenceUtils.getValue(getActivity(), Constant.MAX_CIRCLE, 0));
        }
    }

    private void getPreferencesData() {

        wTime = PreferenceUtils.getValue(getActivity(), Constant.S_WTIME, wTime);
        bTime = PreferenceUtils.getValue(getActivity(), Constant.S_BTIME, bTime);
        lBTime = PreferenceUtils.getValue(getActivity(), Constant.S_LBTIME, lBTime);
        nOSess = PreferenceUtils.getValue(getActivity(), Constant.S_NOSESS, nOSess);
        vol = PreferenceUtils.getValue(getActivity(), Constant.S_VOL, vol);
        lBTimeMode = PreferenceUtils.getValue(getActivity(), Constant.S_LBTIME_MODE, lBTimeMode);
        shakeMode = PreferenceUtils.getValue(getActivity(), Constant.S_SHAKE_MODE, shakeMode);
        soundMode = PreferenceUtils.getValue(getActivity(), Constant.S_SOUND_MODE, soundMode);
        silenceMode = PreferenceUtils.getValue(getActivity(), Constant.S_SILENCE_MODE, silenceMode);

        if (!isBound) {
            HelperClass.getTasks(getActivity());
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imb_startCD:
                startAction();
                break;
            case R.id.imb_break:
                breakAction();
                break;
            case R.id.imb_pause:
                pauseAction();
                break;
            case R.id.imb_skip:
                skipAction();
                break;
            case R.id.imb_stop:

                stopAction();

                break;
        }
    }

    private void breakAction() {
        isCounting = true;
        isWorking = false;
        imb_start.setVisibility(View.GONE);
        imb_break.setVisibility(View.GONE);
        imb_skipNext.setVisibility(View.VISIBLE);
        imb_pause.setVisibility(View.VISIBLE);
        imb_stop.setVisibility(View.VISIBLE);
        setUpService();
        countdownClock.setMaxValue(bTime * 60);
//        PreferenceUtils.setValue(getActivity(), calendar.get(Calendar.DATE) + "work", PreferenceUtils.getValue(getActivity(), calendar.get(Calendar.DATE) + "work", 0) + 1);
        PreferenceUtils.setValue(getActivity(), Constant.MAX_CIRCLE, bTime * 60);
        countdownClock.setRimColor(getResources().getColor(R.color.backgroud_circle));
        GlideUtils.loadCircleImage(getActivity(), "https://thumbs.dreamstime.com/t/businessman-top-mountain-sitting-thinking-image-young-who-sits-looks-distance-to-beautiful-mountains-58395281.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void pauseAction() {
        if (isCounting) {
            imb_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            isCounting = false;

            mService.mCountdownTimer.cancel();
        } else {
            isCounting = true;
            mService.setUpCountdownTimer(mService.millisLeft);
        }
    }

    private void skipAction() {
        if (isOnSess) {
            if (isWorking) {
                isWorking = false;
                countdownClock.setMaxValue(bTime * 60);
                GlideUtils.loadCircleImage(getActivity(), "https://thumbs.dreamstime.com/t/businessman-top-mountain-sitting-thinking-image-young-who-sits-looks-distance-to-beautiful-mountains-58395281.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
            } else {
                isWorking = true;
                countdownClock.setMaxValue(wTime * 60);
                GlideUtils.loadCircleImage(getActivity(), "https://thumbs.dreamstime.com/z/happy-businessman-work-below-black-clouds-white-background-35044869.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
            }
        } else {

        }
        isCounting = true;

        imb_start.setVisibility(View.GONE);
        imb_break.setVisibility(View.GONE);
        imb_skipNext.setVisibility(View.VISIBLE);
        imb_pause.setVisibility(View.VISIBLE);
        imb_stop.setVisibility(View.VISIBLE);
        mService.mCountdownTimer.cancel();
        mService.stopSelf();
        setUpService();
    }

    private void startAction() {
        isCounting = true;
        isWorking = true;

        imb_start.setVisibility(View.GONE);
        imb_break.setVisibility(View.GONE);
        imb_skipNext.setVisibility(View.VISIBLE);
        imb_pause.setVisibility(View.VISIBLE);
        imb_stop.setVisibility(View.VISIBLE);
        setUpService();
        countdownClock.setMaxValue(wTime * 60);
        PreferenceUtils.setValue(getActivity(), Constant.MAX_CIRCLE, wTime * 60);
        countdownClock.setRimColor(getResources().getColor(R.color.backgroud_circle));
        GlideUtils.loadCircleImage(getActivity(), "https://thumbs.dreamstime.com/z/happy-businessman-work-below-black-clouds-white-background-35044869.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);
    }

    private void stopAction() {
        if (isWorking) {
            fm = getActivity().getFragmentManager();
            AddingTaskDialogFragment dialogFragment = new AddingTaskDialogFragment();
            dialogFragment.show(fm, "Sample Fragment");
        }
        isCounting = false;
        isOnSess = false;
        isWorking = false;
        hasNext = false;
        tv_countdown.setText(wTime + getResources().getString(R.string.time_0));
        imb_start.setVisibility(View.VISIBLE);
        imb_break.setVisibility(View.VISIBLE);
        imb_skipNext.setVisibility(View.GONE);
        imb_pause.setVisibility(View.GONE);
        imb_stop.setVisibility(View.GONE);
        mService.mCountdownTimer.cancel();
        mService.stopForeground(true);
        mService.stopSelf();
        HelperClass.saveTasks(getActivity());
        countdownClock.setRimColor(getResources().getColor(R.color.colorPrimary));
        countdownClock.setValue(0);
        countdownClock.setRimColor(getResources().getColor(R.color.colorPrimary));
        GlideUtils.loadCircleImage(getActivity(), "https://migreat.files.wordpress.com/2014/07/man-with-laptop-sitting-and-thinking-baloon-in-background-flickr-nic519.jpg", R.mipmap.ic_launcher, R.mipmap.ic_launcher, ivClock);


    }

    private void setUpService() {
// Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                CountdownService.MyBinder binder = (CountdownService.MyBinder) service;
                mService = binder.getService(); // lấy đối tượng MyService
            }
        };
        startNewCountdownService();

    }

    private void startNewCountdownService() {
        Intent intent = new Intent(getActivity(), CountdownService.class);
        int a;
        if (isWorking) {
            a = wTime;
        } else {
            a = bTime;
        }
        intent.putExtra(TIME_IN_MIN, a);
        getActivity().startService(intent);
        isOnSess = true;
        if (isBound == false) {
            getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }

    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(mService.ACTION_FINISH)) {
                hasNext = true;
                isOnSess = false;
                notifyUser();
                if (isWorking) {
                    tv_countdown.setText(bTime + " : 00");
                    imb_break.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                    imb_start.setVisibility(View.GONE);

                } else {
                    tv_countdown.setText(wTime + " : 00");
                    imb_start.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                    imb_break.setVisibility(View.GONE);

                }
                mService.setupNotification();
            } else if (intent.getAction().equals(mService.ACTION_STOP)) {
                stopAction();
                mService.setupNotification();
            } else if (intent.getAction().equals(mService.ACTION_NEXT)) {
                skipAction();
                mService.setupNotification();
            } else if (intent.getAction().equals(mService.ACTION_PAUSE)) {
                if (isOnSess) {
                    pauseAction();
                } else {
                    if (isWorking) {
                        breakAction();
                    } else {
                        startAction();
                    }
                }
                mService.setupNotification();
            }

        }
    }

    private void notifyUser() {
        if (shakeMode) {
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(1000);

        }
        if (soundMode) {
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getActivity(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (silenceMode) {
            AudioManager am;
            am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }
    }

}
