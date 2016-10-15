package com.example.huy.managertimer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.fragment.TaskFragment;

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
        edt_name.setText(TaskFragment.tasks.get(pos).getTitle());
        tv_beginDate.setText(TaskFragment.tasks.get(pos).getBeginDate());
        tv_totalTime.setText(TaskFragment.tasks.get(pos).getWTime()+"");
        tv_timeAve.setText(TaskFragment.tasks.get(pos).getAveWTime()+"");
    }
}
