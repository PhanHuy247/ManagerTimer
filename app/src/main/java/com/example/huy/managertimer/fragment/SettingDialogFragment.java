package com.example.huy.managertimer.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huy.managertimer.R;

/**
 * Created by Laptop88 on 10/11/2016.
 */

public class SettingDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_setting, container, false);
        getDialog().setTitle("Setting");
        return rootView;
    }
}
