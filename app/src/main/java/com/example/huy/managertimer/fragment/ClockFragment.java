package com.example.huy.managertimer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.huy.managertimer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment implements View.OnClickListener{

    TextView tv_countdown;
    ImageButton imb_start, imb_skipNext, imb_pause, imb_break, imb_stop;
    public static boolean isCounting;
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
        imb_start = (ImageButton) view.findViewById(R.id.imb_startCD);
        imb_skipNext = (ImageButton) view.findViewById(R.id.imb_skip);
        imb_pause = (ImageButton) view.findViewById(R.id.imb_pause);
        imb_break = (ImageButton) view.findViewById(R.id.imb_break);
        imb_stop = (ImageButton) view.findViewById(R.id.imb_stop);
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
                break;
            case R.id.imb_break:
                isCounting = true;
                imb_start.setVisibility(View.GONE);
                imb_break.setVisibility(View.GONE);
                imb_skipNext.setVisibility(View.VISIBLE);
                imb_pause.setVisibility(View.VISIBLE);
                imb_stop.setVisibility(View.VISIBLE);
                break;
            case R.id.imb_pause:
                if (isCounting){
                    imb_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    isCounting = false;
                }
                else {
                    imb_pause.setImageResource(R.drawable.ic_pause_black_24dp);
                    isCounting = true;
                }
                break;
            case R.id.imb_skip:
                break;
            case R.id.imb_stop:
                isCounting = false;
                imb_start.setVisibility(View.VISIBLE);
                imb_break.setVisibility(View.VISIBLE);
                imb_skipNext.setVisibility(View.GONE);
                imb_pause.setVisibility(View.GONE);
                imb_stop.setVisibility(View.GONE);
                break;
        }
    }
}
