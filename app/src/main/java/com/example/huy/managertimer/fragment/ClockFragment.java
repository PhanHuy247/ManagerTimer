package com.example.huy.managertimer.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import com.example.huy.managertimer.activity.SettingActivity;
import com.example.huy.managertimer.services.CountdownService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment implements View.OnClickListener{
    public static CountdownService mService = null;
    public static boolean isWorking;
//    public static boolean isRelaxing;
    public static TextView tv_countdown;
    ImageButton imb_start, imb_skipNext, imb_break, imb_stop;
    public static ImageButton imb_pause;
    public static boolean isCounting;
    private boolean isBound = false;
    ServiceConnection connection;
    public ClockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clock, container, false);
        initView(view);
        setOnClick();
        return view;
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
        setTextForCountdownTimer();
        imb_start = (ImageButton) view.findViewById(R.id.imb_startCD);
        imb_skipNext = (ImageButton) view.findViewById(R.id.imb_skip);
        imb_pause = (ImageButton) view.findViewById(R.id.imb_pause);
        imb_break = (ImageButton) view.findViewById(R.id.imb_break);
        imb_stop = (ImageButton) view.findViewById(R.id.imb_stop);
    }

    private void setTextForCountdownTimer() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imb_startCD:
                isCounting = true;
                imb_start.setVisibility(View.GONE);
                imb_break.setVisibility(View.GONE);
                imb_skipNext.setVisibility(View.VISIBLE);
                imb_pause.setVisibility(View.VISIBLE);
                imb_stop.setVisibility(View.VISIBLE);
                isWorking = true;
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
                isCounting = true;
                if (isWorking){
                    isWorking = false;
                }
                else {
                    isWorking = true;
                }
                mService.mCountdownTimer.cancel();
                setUpService();
                break;
            case R.id.imb_stop:
                isCounting = false;
                imb_start.setVisibility(View.VISIBLE);
                imb_break.setVisibility(View.VISIBLE);
                imb_skipNext.setVisibility(View.GONE);
                imb_pause.setVisibility(View.GONE);
                imb_stop.setVisibility(View.GONE);
                mService.stopSelf();
                mService.mCountdownTimer.cancel();
                break;
        }
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
        startNewMusicService();

    }
    private void startNewMusicService (){
        Intent intent = new Intent(getActivity(), CountdownService.class);
        int a;
        if (isWorking){
            a = SettingActivity.wTime;
        }
        else {
            a= SettingActivity.bTime;
        }
        intent.putExtra("timeInMin", a);
        getActivity().startService(intent);
        if (isBound==false){
            getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }


}
