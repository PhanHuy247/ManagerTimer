package com.example.huy.managertimer.fragment;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;
import com.example.huy.managertimer.services.CountdownService;
import com.google.gson.Gson;

import static com.example.huy.managertimer.activity.SettingActivity.S_BTIME;
import static com.example.huy.managertimer.activity.SettingActivity.S_LBTIME;
import static com.example.huy.managertimer.activity.SettingActivity.S_LBTIME_MODE;
import static com.example.huy.managertimer.activity.SettingActivity.S_NOSESS;
import static com.example.huy.managertimer.activity.SettingActivity.S_SHAKE_MODE;
import static com.example.huy.managertimer.activity.SettingActivity.S_SILENCE_MODE;
import static com.example.huy.managertimer.activity.SettingActivity.S_SOUND_MODE;
import static com.example.huy.managertimer.activity.SettingActivity.S_VOL;
import static com.example.huy.managertimer.activity.SettingActivity.S_WIFI_MODE;
import static com.example.huy.managertimer.activity.SettingActivity.S_WTIME;
import static com.example.huy.managertimer.activity.SettingActivity.bTime;
import static com.example.huy.managertimer.activity.SettingActivity.lBTime;
import static com.example.huy.managertimer.activity.SettingActivity.lBTimeMode;
import static com.example.huy.managertimer.activity.SettingActivity.nOSess;
import static com.example.huy.managertimer.activity.SettingActivity.shakeMode;
import static com.example.huy.managertimer.activity.SettingActivity.silenceMode;
import static com.example.huy.managertimer.activity.SettingActivity.soundMode;
import static com.example.huy.managertimer.activity.SettingActivity.vol;
import static com.example.huy.managertimer.activity.SettingActivity.wTime;
import static com.example.huy.managertimer.activity.SettingActivity.wifiMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment implements View.OnClickListener{
    public static CountdownService mService = null;
    public static boolean isWorking;
    public static final Task DEF_TASK = new Task(0, "");
//    public static boolean isRelaxing;
    public static  Task curTask = DEF_TASK;
    public static TextView tv_countdown;
    ImageButton imb_start, imb_skipNext, imb_break, imb_stop;
    ImageButton imb_isWorking, imb_isRelaxing;
    TextView tv_taskTitle;
    public static ImageButton imb_pause;
    public static boolean isCounting;
    public static boolean isOnSess;
    public static boolean hasNext;
    public static int position = -1;
    private boolean isBound = false;
    ServiceConnection connection;
    public static String curCountdownStr;
    private MyBroadcastReceiver broadcastReceiver;
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
        tv_taskTitle.setText(TaskFragment.tasks.get(position).getTitle());

        imb_start.setVisibility(View.GONE);
        imb_break.setVisibility(View.GONE);
        imb_skipNext.setVisibility(View.VISIBLE);
        imb_pause.setVisibility(View.VISIBLE);
        imb_stop.setVisibility(View.VISIBLE);
        imb_isWorking.setVisibility(View.VISIBLE);
        imb_isRelaxing.setVisibility(View.GONE);
        tv_countdown.setText(wTime+" : 00");
        isOnSess = true;
        isWorking = true;
        isCounting = true;
        if (mService!=null){
            mService.mCountdownTimer.cancel();
            mService.setUpCountdownTimer(wTime*60000);
        }
        else {
            setUpService();
        }


    }

    private void setBroadcastReceiver() {
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(mService.ACTION_FINISH);
        filter.addAction(mService.ACTION_STOP);
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
        imb_isWorking = (ImageButton) view.findViewById(R.id.imb_isWorking);
        imb_isRelaxing = (ImageButton) view.findViewById(R.id.imb_isRelaxing);
        tv_taskTitle = (TextView) view.findViewById(R.id.tv_taskTitle);
        if (!isCounting){
            imb_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        }
        getPreferencesData();
        int temp = getActivity().getIntent().getIntExtra("position", -1);
        if (position!=temp && temp!=-1){
            position = temp;
            resetCoundownTimer(position);
        }
        else {
            String tmp;
            if (position==-1){
                 tmp = "";

            }
            else {
                tmp = TaskFragment.tasks.get(position).getTitle();
            }
            tv_taskTitle.setText(tmp);

        }
        if (isOnSess){
            tv_countdown.setText(curCountdownStr);
            imb_start.setVisibility(View.GONE);
            imb_break.setVisibility(View.GONE);
            imb_skipNext.setVisibility(View.VISIBLE);
            imb_pause.setVisibility(View.VISIBLE);
            imb_stop.setVisibility(View.VISIBLE);
            if (isWorking){
                imb_isWorking.setVisibility(View.VISIBLE);
                imb_isRelaxing.setVisibility(View.GONE);
            }
            else {
                imb_isWorking.setVisibility(View.GONE);
                imb_isRelaxing.setVisibility(View.VISIBLE);
            }

        }
        else {
            if (hasNext){
                if (isWorking){
                    tv_countdown.setText(""+bTime+" : 00");
                    imb_start.setVisibility(View.GONE);
                    imb_break.setVisibility(View.VISIBLE);
                    imb_skipNext.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                }
                else {
                    tv_countdown.setText(""+wTime+" : 00");
                    imb_start.setVisibility(View.VISIBLE);
                    imb_break.setVisibility(View.GONE);
                    imb_skipNext.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                }
            }
            else {
                tv_countdown.setText(""+wTime+" : 00");
                imb_start.setVisibility(View.VISIBLE);
                imb_break.setVisibility(View.VISIBLE);
                imb_skipNext.setVisibility(View.GONE);
                imb_pause.setVisibility(View.GONE);
                imb_stop.setVisibility(View.GONE);
            }



        }
    }

    private void getPreferencesData() {
        SharedPreferences settingPrefs = getActivity().getSharedPreferences(getString(R.string.setting_pref), Context.MODE_PRIVATE);
        wTime = settingPrefs.getInt(S_WTIME, wTime);
        bTime = settingPrefs.getInt(S_BTIME, bTime);
        lBTime = settingPrefs.getInt(S_LBTIME, lBTime);
        nOSess = settingPrefs.getInt(S_NOSESS, nOSess);
        vol = settingPrefs.getInt(S_VOL, vol);
        lBTimeMode = settingPrefs.getBoolean(S_LBTIME_MODE, lBTimeMode);
        shakeMode = settingPrefs.getBoolean(S_SHAKE_MODE, shakeMode);
        soundMode = settingPrefs.getBoolean(S_SOUND_MODE, soundMode);
        silenceMode = settingPrefs.getBoolean(S_SILENCE_MODE, silenceMode);
        wifiMode = settingPrefs.getBoolean(S_WIFI_MODE, wifiMode);

//        if (!isBound){
//            SharedPreferences tasksPrefs = getActivity().getSharedPreferences(getString(R.string.tasks_infos), Context.MODE_PRIVATE);
//            Gson gson = new Gson();
//            for (int i = 0; i < TaskFragment.tasks.size(); i++) {
//
//                String json = tasksPrefs.getString(TaskFragment.tasks.get(i).getTitle(), "");
//                Task tmp = gson.fromJson(json, Task.class);
//                if (tmp!=null){
//                    TaskFragment.tasks.set(i, tmp);
//                }
//
//            }
//            String json = tasksPrefs.getString(getString(R.string.defaultTask), "");
//            Task tmp = gson.fromJson(json, Task.class);
//            if (tmp!=null){
//                TaskFragment.defaultTask = tmp;
//            }
//            Log.d("getData", TaskFragment.defaultTask.getWTime()+"");
//            for (int i = 0; i < TaskFragment.tasks.size(); i++) {
//                Log.d("getData"+(i+1), TaskFragment.tasks.get(i).getWTime()+"");
//            }
//        }


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imb_startCD:
                isCounting = true;
                isWorking = true;

                imb_start.setVisibility(View.GONE);
                imb_break.setVisibility(View.GONE);
                imb_skipNext.setVisibility(View.VISIBLE);
                imb_pause.setVisibility(View.VISIBLE);
                imb_stop.setVisibility(View.VISIBLE);
                setUpService();
                break;
            case R.id.imb_break:
                isCounting = true;
                isWorking = false;
                imb_start.setVisibility(View.GONE);
                imb_break.setVisibility(View.GONE);
                imb_skipNext.setVisibility(View.VISIBLE);
                imb_pause.setVisibility(View.VISIBLE);
                imb_stop.setVisibility(View.VISIBLE);
                setUpService();
                break;
            case R.id.imb_pause:
                if (isCounting){
                    imb_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    isCounting = false;

                    mService.mCountdownTimer.cancel();
                }
                else {
                    isCounting = true;
                    mService.setUpCountdownTimer(mService.millisLeft);
                }
                break;
            case R.id.imb_skip:
                if (isOnSess){
                    if (isWorking){
                        isWorking = false;
                    }
                    else {
                        isWorking = true;
                    }
                }
                else {
                    Log.d("khuong", isWorking+"");
                }


                isCounting = true;

                imb_start.setVisibility(View.GONE);
                imb_break.setVisibility(View.GONE);
                imb_skipNext.setVisibility(View.VISIBLE);
                imb_pause.setVisibility(View.VISIBLE);
                imb_stop.setVisibility(View.VISIBLE);
                mService.mCountdownTimer.cancel();
                setUpService();
                break;
            case R.id.imb_stop:
                stopAction();
                mService.stopForeground(true);
                break;
        }
    }

    private void stopAction() {
        isCounting = false;
        isOnSess = false;
        tv_countdown.setText(""+wTime+" : 00");
        imb_isWorking.setVisibility(View.GONE);
        imb_isRelaxing.setVisibility(View.GONE);
        imb_start.setVisibility(View.VISIBLE);
        imb_break.setVisibility(View.VISIBLE);
        imb_skipNext.setVisibility(View.GONE);
        imb_pause.setVisibility(View.GONE);
        imb_stop.setVisibility(View.GONE);
        mService.mCountdownTimer.cancel();

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
    private void startNewCountdownService(){
        Intent intent = new Intent(getActivity(), CountdownService.class);
        int a;
        if (isWorking){
            a = wTime;
            imb_isWorking.setVisibility(View.VISIBLE);
            imb_isRelaxing.setVisibility(View.GONE);
        }
        else {
            a= bTime;
            imb_isRelaxing.setVisibility(View.VISIBLE);
            imb_isWorking.setVisibility(View.GONE);
        }
        isOnSess = true;
        if (isBound==false){
            intent.putExtra("timeInMin", a);
            getActivity().startService(intent);
            getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
        else {

            mService.setUpCountdownTimer(a*60000);

        }
    }
    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(mService.ACTION_FINISH)){

                imb_isWorking.setVisibility(View.GONE);
                imb_isRelaxing.setVisibility(View.GONE);
                if (isWorking){
                    tv_countdown.setText(bTime+" : 00");
                    imb_break.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                    imb_start.setVisibility(View.GONE);

                }
                else {
                    tv_countdown.setText(wTime+" : 00");
                    imb_start.setVisibility(View.VISIBLE);
                    imb_pause.setVisibility(View.GONE);
                    imb_stop.setVisibility(View.GONE);
                    imb_break.setVisibility(View.GONE);

                }
            }
            else if (intent.getAction().equals(mService.ACTION_STOP)){
                stopAction();
            }
        }
    }


}
