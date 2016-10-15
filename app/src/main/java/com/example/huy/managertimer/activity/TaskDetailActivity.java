package com.example.huy.managertimer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;
import com.example.huy.managertimer.fragment.TaskFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity {
    EditText edt_name;
    TextView tv_beginDate;
    TextView tv_totalTime;
    TextView tv_timeAve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        initView();
    }
    private void initView() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        tv_beginDate = (TextView) findViewById(R.id.tv_beginDate);
        tv_totalTime = (TextView) findViewById(R.id.tv_workTimeTotal);
        tv_timeAve = (TextView) findViewById(R.id.tv_workTimeAve);
        getTaskInfo();
    }

    private void getTaskInfo() {
        Intent intent = getIntent();
        int pos = intent.getIntExtra(getString(R.string.TASK_POS), 0);
        ArrayList<Task> taskArrayList = TaskFragment.tasks;
        edt_name.setText(taskArrayList.get(pos).getTitle());
        String beginDate = taskArrayList.get(pos).getBeginDate();
        tv_beginDate.setText(beginDate);
        int wTime = taskArrayList.get(pos).getWTime();
        tv_totalTime.setText(wTime+"");
        int day=0;
        try {
            DateFormat df = new SimpleDateFormat(getString(R.string.dateFormat));
            Date date1 = new Date();
            Date date2 = df.parse(beginDate);
            long diff = date1.getTime() - date2.getTime();
            day = (int) diff/1000/60/60/24;
            Log.e("TEST" , diff+"");
        } catch (ParseException e) {
            Log.e("TEST", "Exception", e);
        }
        if (day==0){
            day=1;
        }
        int aveTime = wTime/day;
        tv_timeAve.setText(aveTime+"");
    }
}
