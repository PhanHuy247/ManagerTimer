package com.example.huy.managertimer.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.constant.Constant;
import com.example.huy.managertimer.databases.HelperClass;
import com.example.huy.managertimer.model.TaskItem;
import com.example.huy.managertimer.utilities.PreferenceUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laptop88 on 10/11/2016.
 */

public class AddingTaskDialogFragment extends DialogFragment implements View.OnClickListener {
    EditText edt_title;
    Button imb_cancel, imb_save;
    TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_task, container, false);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.FILL_HORIZONTAL;
        }
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        edt_title = (EditText) rootView.findViewById(R.id.edt_title);
        imb_cancel = (Button) rootView.findViewById(R.id.imb_cancle);
        imb_save = (Button) rootView.findViewById(R.id.imb_save);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        imb_cancel.setOnClickListener(this);
        imb_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imb_cancle:
                dismiss();
                break;
            case R.id.imb_save:
                if (!edt_title.getText().equals("")) {
                    DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat));
                    Date date = new Date();
                    TaskItem nTaskItem = new TaskItem(PreferenceUtils.getValue(getActivity(), Constant.TIME_STOP, 0), edt_title.getText().toString(), dateFormat.format(date));
                    TaskFragment.taskItems.add(nTaskItem);
                    TaskFragment.taskAdapter.notifyDataSetChanged();
                    HelperClass.saveTasks(getActivity());
                    dismiss();
                }
                break;
        }
    }
}
