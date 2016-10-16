package com.example.huy.managertimer.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.constant.Constant;
import com.example.huy.managertimer.fragment.ClockFragment;
import com.example.huy.managertimer.utilities.CorrectSizeUtil;
import com.example.huy.managertimer.utilities.PreferenceUtils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_wTime, tv_bTime, tv_longBTime;
    TextView tv_SessBefLongBTime;
    TextView tv_SessBefLongBreak;
    TextView tv_vol;
    TextView tv_longBreak;
    TextView tv_typeSound;
    ImageView ivArrowSetting;
    SeekBar sb_wTime, sb_bTime, sb_longBTime;
    SeekBar sb_volume;
    SeekBar sb_SessBefLongBTime;
    Switch sw_longBTime, sw_shakeMode, sw_soundMode, sw_silenceMode, sw_wifiMode;
    Spinner sp_typeSound;

    public static int wTime = 25;
    public static int bTime = 5;
    public static int lBTime = 20;
    public static int nOSess = 4;
    public static int vol = 50;
    public static boolean lBTimeMode = true;
    public static boolean shakeMode = true;
    public static boolean soundMode = true;
    public static boolean silenceMode = false;

    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        CorrectSizeUtil.getInstance(this).correctSize(findViewById(R.id.rlSetting));
        initView();
        initSetting();
        setOnViewClick();
        setOnWifi();
    }

    private void setOnWifi() {

        sw_wifiMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    wifiManager.setWifiEnabled(true);
                } else {
                    wifiManager.setWifiEnabled(false);
                }
            }
        });
    }

    private void setOnViewClick() {
        setOnSeekBarChange(sb_wTime, tv_wTime);
        setOnSeekBarChange(sb_bTime, tv_bTime);
        setOnSeekBarChange(sb_longBTime, tv_longBTime);
        setOnSeekBarChange(sb_SessBefLongBTime, tv_SessBefLongBTime);

        ivArrowSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sw_longBTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tv_longBTime.setVisibility(View.VISIBLE);
                    tv_longBreak.setVisibility(View.VISIBLE);
                    tv_SessBefLongBTime.setVisibility(View.VISIBLE);
                    tv_SessBefLongBreak.setVisibility(View.VISIBLE);
                    sb_longBTime.setVisibility(View.VISIBLE);
                    sb_SessBefLongBTime.setVisibility(View.VISIBLE);
                    lBTimeMode = true;
                } else {

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
                if (b) {
                    shakeMode = true;
                } else {
                    shakeMode = false;
                }
            }
        });

        sw_soundMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    soundMode = true;
                    tv_typeSound.setVisibility(View.VISIBLE);
                    sp_typeSound.setVisibility(View.VISIBLE);
                    tv_vol.setVisibility(View.VISIBLE);
                    sb_volume.setVisibility(View.VISIBLE);
                } else {
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
                if (b) {
                    silenceMode = true;
                } else {
                    silenceMode = false;
                }
            }
        });

    }

    private void setOnSeekBarChange(SeekBar sb, final TextView tv) {

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText(i + "");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() == 0) {
                    seekBar.setProgress(1);
                }
            }
        });
    }

    private void initSetting() {


        tv_wTime.setText(wTime + "");
        tv_bTime.setText(bTime + "");
        tv_longBTime.setText(lBTime + "");
        tv_SessBefLongBTime.setText(nOSess + "");

        sb_wTime.setProgress(wTime);
        sb_bTime.setProgress(bTime);
        sb_longBTime.setProgress(lBTime);
        sb_SessBefLongBTime.setProgress(nOSess);
        sb_volume.setProgress(vol);

        sw_wifiMode.setChecked(wifiManager.isWifiEnabled());
        sw_silenceMode.setChecked(silenceMode);
        sw_soundMode.setChecked(soundMode);
        sw_shakeMode.setChecked(shakeMode);
        sw_longBTime.setChecked(lBTimeMode);

        if (!lBTimeMode) {
            tv_longBTime.setVisibility(View.GONE);
            tv_longBreak.setVisibility(View.GONE);
            tv_SessBefLongBTime.setVisibility(View.GONE);
            tv_SessBefLongBreak.setVisibility(View.GONE);
            sb_longBTime.setVisibility(View.GONE);
            sb_SessBefLongBTime.setVisibility(View.GONE);
        }
        if (!soundMode) {
            tv_typeSound.setVisibility(View.GONE);
            sp_typeSound.setVisibility(View.GONE);
            tv_vol.setVisibility(View.GONE);
            sb_volume.setVisibility(View.GONE);
        }
    }

    private void initView() {
        tv_wTime = (TextView) findViewById(R.id.tv_workTime);
        tv_bTime = (TextView) findViewById(R.id.tv_breakTime);
        tv_longBTime = (TextView) findViewById(R.id.tv_longBreakTime);
        tv_vol = (TextView) findViewById(R.id.tv_volume);
        tv_longBreak = (TextView) findViewById(R.id.tv_lBreak);
        tv_SessBefLongBTime = (TextView) findViewById(R.id.tv_SessBefLongBTime);
        tv_SessBefLongBreak = (TextView) findViewById(R.id.tv_SessBefLongBreak);
        tv_typeSound = (TextView) findViewById(R.id.tv_typeSound);
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
        ivArrowSetting = (ImageView) findViewById(R.id.ivArrowSetting);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        sb_wTime.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb_bTime.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb_volume.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb_longBTime.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        sb_SessBefLongBTime.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        wTime = sb_wTime.getProgress();
//        if(wTime < 10) wTime = 10;
        bTime = sb_bTime.getProgress();
//        if(bTime < 1) bTime = 1;
        lBTime = sb_longBTime.getProgress();
//        if(lBTime < 30) lBTime = 30;
        nOSess = sb_SessBefLongBTime.getProgress();
//        if(nOSess < 3) nOSess = 3;
        vol = sb_volume.getProgress();

        if (ClockFragment.mService == null) {
            ClockFragment.tv_countdown.setText(wTime + getResources().getString(R.string.time_0));
        } else {
            if (!ClockFragment.isOnSess) {
                if (ClockFragment.isWorking) {
                    ClockFragment.tv_countdown.setText(bTime + getResources().getString(R.string.time_0));
                } else {
                    ClockFragment.tv_countdown.setText(wTime + getResources().getString(R.string.time_0));
                }
            }
        }
        PreferenceUtils.setValue(this, Constant.S_WTIME, wTime);
        PreferenceUtils.setValue(this, Constant.S_BTIME, bTime);
        PreferenceUtils.setValue(this, Constant.S_LBTIME, lBTime);
        PreferenceUtils.setValue(this, Constant.S_NOSESS, nOSess);
        PreferenceUtils.setValue(this, Constant.S_VOL, vol);
        PreferenceUtils.setValue(this, Constant.S_LBTIME_MODE, lBTimeMode);
        PreferenceUtils.setValue(this, Constant.S_SHAKE_MODE, shakeMode);
        PreferenceUtils.setValue(this, Constant.S_SOUND_MODE, soundMode);
        PreferenceUtils.setValue(this, Constant.S_SILENCE_MODE, silenceMode);

        super.onStop();
    }

    @Override
    public void onClick(View v) {

    }
}
