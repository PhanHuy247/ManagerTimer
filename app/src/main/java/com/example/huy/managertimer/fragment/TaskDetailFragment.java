package com.example.huy.managertimer.fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huy.managertimer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends DialogFragment {
    EditText edt_name;
    TextView tv_beginDate;
    TextView tv_totalTime;
    TextView tv_timeAve;


    public TaskDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        getDialog().setTitle("Task");
        initView(view);
        return view;
    }

    private void initView(View view) {
        edt_name = (EditText) view.findViewById(R.id.edt_name);
        tv_beginDate = (TextView) view.findViewById(R.id.tv_beginDate);
        tv_totalTime = (TextView) view.findViewById(R.id.tv_workTimeTotal);
        tv_timeAve = (TextView) view.findViewById(R.id.tv_workTimeAve);
        getTaskInfo();
    }

    private void getTaskInfo() {
        Intent intent = getActivity().getIntent();
        int pos = intent.getIntExtra(getString(R.string.TASK_POS), 0);
        edt_name.setText(TaskFragment.tasks.get(pos).getTitle());
        tv_beginDate.setText(TaskFragment.tasks.get(pos).getBeginDate());
        tv_totalTime.setText(TaskFragment.tasks.get(pos).getWTime()+"");
        tv_timeAve.setText(TaskFragment.tasks.get(pos).getAveWTime()+"");
    }

}
