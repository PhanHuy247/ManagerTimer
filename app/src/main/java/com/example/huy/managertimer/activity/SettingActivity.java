package com.example.huy.managertimer.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.huy.managertimer.R;

public class SettingActivity extends AppCompatActivity{
    TextView tv_wTime, tv_bTime, tv_longBTime;
    TextView tv_SessBefLongBTime;
    TextView tv_SessBefLongBreak;
    TextView tv_vol;
    TextView tv_longBreak;
    TextView tv_typeSound;
    SeekBar sb_wTime, sb_bTime, sb_longBTime;
    SeekBar sb_volume;
    SeekBar sb_SessBefLongBTime;
    Switch sw_longBTime, sw_shakeMode, sw_soundMode, sw_silenceMode, sw_wifiMode;
    Spinner sp_typeSound;

    public static  int wTime = 25;
    public static  int bTime = 5;
    public static  int lBTime = 20;
    public static  int nOSess = 4;
    public static  int vol = 50;
    public static boolean lBTimeMode = true;
    public static boolean shakeMode = true;
    public static boolean soundMode = true;
    public static boolean silenceMode = false;
    public static boolean wifiMode = false;

    public static final String S_WTIME = "saved_wTime";
    public static final String S_BTIME = "saved_bTime";
    public static final String S_LBTIME = "saved_lBTime";
    public static final String S_NOSESS = "saved_nOSess";
    public static final String S_VOL = "saved_vol";
    public static final String S_LBTIME_MODE = "saved_lBTime_mode";
    public static final String S_SHAKE_MODE = "saved_shake_mode";
    public static final String S_SOUND_MODE = "saved_sound_mode";
    public static final String S_SILENCE_MODE = "saved_silence_mode";
    public static final String S_WIFI_MODE = "saved_wifi_mode";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initSetting();
        setOnViewClick();
    }

    private void setOnViewClick() {
        setOnSeekBarChange(sb_wTime, tv_wTime);
        setOnSeekBarChange(sb_bTime, tv_bTime);
        setOnSeekBarChange(sb_longBTime, tv_longBTime);
        setOnSeekBarChange(sb_SessBefLongBTime, tv_SessBefLongBTime);

        sw_longBTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tv_longBTime.setVisibility(View.VISIBLE);
                    tv_longBreak.setVisibility(View.VISIBLE);
                    tv_SessBefLongBTime.setVisibility(View.VISIBLE);
                    tv_SessBefLongBreak.setVisibility(View.VISIBLE);
                    sb_longBTime.setVisibility(View.VISIBLE);
                    sb_SessBefLongBTime.setVisibility(View.VISIBLE);
                    lBTimeMode = true;
                }
                else {

                    tv_longBTime.setVisibility(View.GONE);
                    tv_longBreak.setVisibility(View.GONE);
                    tv_SessBefLongBTime.setVisibility(View.GONE);
                    tv_SessBefLongBreak.setVisibility(View.GONE);
                    sb_longBTime.setVisibility(View.GONE);
                    sb_SessBefLongBTime.setVisibility(View.GONE);
                    lBTimeMode = false;
                }
            }
        });

        sw_shakeMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    shakeMode = true;
                }
                else {
                    shakeMode = false;
                }
            }
        });

        sw_soundMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    soundMode = true;
                    tv_typeSound.setVisibility(View.VISIBLE);
                    sp_typeSound.setVisibility(View.VISIBLE);
                    tv_vol.setVisibility(View.VISIBLE);
                    sb_volume.setVisibility(View.VISIBLE);
                }
                else {
                    soundMode = false;
                    tv_typeSound.setVisibility(View.GONE);
                    sp_typeSound.setVisibility(View.GONE);
                    tv_vol.setVisibility(View.GONE);
                    sb_volume.setVisibility(View.GONE);
                }
            }
        });

        sw_silenceMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    silenceMode = true;
                }
                else {
                    silenceMode = false;
                }
            }
        });

        sw_wifiMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    wifiMode = true;
                }
                else {
                    wifiMode = false;
                }
            }
        });
    }
    private void setOnSeekBarChange(SeekBar sb, final TextView tv){
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress()==0){
                    seekBar.setProgress(1);
                }
            }
        });
    }

    private void initSetting() {



        tv_wTime.setText(wTime+"");
        tv_bTime.setText(bTime+"");
        tv_longBTime.setText(lBTime+"");
        tv_SessBefLongBTime.setText(nOSess+"");

        sb_wTime.setProgress(wTime);
        sb_bTime.setProgress(bTime);
        sb_longBTime.setProgress(lBTime);
        sb_SessBefLongBTime.setProgress(nOSess);
        sb_volume.setProgress(vol);

        sw_wifiMode.setChecked(wifiMode);
        sw_silenceMode.setChecked(silenceMode);
        sw_soundMode.setChecked(soundMode);
        sw_shakeMode.setChecked(shakeMode);
        sw_longBTime.setChecked(lBTimeMode);

        if (!lBTimeMode){
            tv_longBTime.setVisibility(View.GONE);
            tv_longBreak.setVisibility(View.GONE);
            tv_SessBefLongBTime.setVisibility(View.GONE);
            tv_SessBefLongBreak.setVisibility(View.GONE);
            sb_longBTime.setVisibility(View.GONE);
            sb_SessBefLongBTime.setVisibility(View.GONE);
        }
        if (!soundMode){
            tv_typeSound.setVisibility(View.GONE);
            sp_typeSound.setVisibility(View.GONE);
            tv_vol.setVisibility(View.GONE);
            sb_volume.setVisibility(View.GONE);
        }
    }

    private void initView() {
        tv_wTime = (TextView)findViewById(R.id.tv_workTime);
        tv_bTime = (TextView)findViewById(R.id.tv_breakTime);
        tv_longBTime = (TextView)findViewById(R.id.tv_longBreakTime);
        tv_vol = (TextView)findViewById(R.id.tv_volume);
        tv_longBreak = (TextView)findViewById(R.id.tv_lBreak);
        tv_SessBefLongBTime = (TextView)findViewById(R.id.tv_SessBefLongBTime);
        tv_SessBefLongBreak = (TextView)findViewById(R.id.tv_SessBefLongBreak);
        tv_typeSound = (TextView)findViewById(R.id.tv_typeSound);
        sb_wTime = (SeekBar) findViewById(R.id.sb_workTime);
        sb_bTime = (SeekBar) findViewById(R.id.sb_breakTime);
        sb_volume = (SeekBar) findViewById(R.id.sb_volume);
        sb_longBTime = (SeekBar) findViewById(R.id.sb_longBreakTime);
        sb_SessBefLongBTime = (SeekBar) findViewById(R.id.sb_SessBefLongBTime);
        sw_longBTime = (Switch) findViewById(R.id.sw_longBreak);
        sw_shakeMode = (Switch) findViewById(R.id.sw_shakeMode);
        sw_soundMode = (Switch) findViewById(R.id.sw_soundMode);
        sw_silenceMode = (Switch) findViewById(R.id.sw_silenceMode);
        sw_wifiMode = (Switch) findViewById(R.id.sw_wifiMode);
        sp_typeSound = (Spinner) findViewById(R.id.sp_typeSound);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        wTime = sb_wTime.getProgress();
        bTime = sb_bTime.getProgress();
        lBTime = sb_longBTime.getProgress();
        nOSess = sb_SessBefLongBTime.getProgress();
        vol = sb_volume.getProgress();
        SharedPreferences preferences = getSharedPreferences(getString(R.string.setting_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(S_WTIME, wTime);
        editor.putInt(S_BTIME, bTime);
        editor.putInt(S_LBTIME, lBTime);
        editor.putInt(S_NOSESS, nOSess);
        editor.putInt(S_VOL, vol);
        editor.putBoolean(S_LBTIME_MODE, lBTimeMode);
        editor.putBoolean(S_SHAKE_MODE, shakeMode);
        editor.putBoolean(S_SOUND_MODE, soundMode);
        editor.putBoolean(S_SILENCE_MODE, silenceMode);
        editor.putBoolean(S_WIFI_MODE, wifiMode);
        editor.commit();
        super.onStop();
    }

}
