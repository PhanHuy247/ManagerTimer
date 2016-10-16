package com.example.huy.managertimer.fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huy.managertimer.Interface.IOnClickListenerTask;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.activity.MainActivity;
import com.example.huy.managertimer.adapter.ViewPageAdapter;
import com.example.huy.managertimer.constant.Constant;
import com.example.huy.managertimer.model.Task;
import com.example.huy.managertimer.adapter.TasksAdapter;
import com.example.huy.managertimer.utilities.PreferenceUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements IOnClickListenerTask{
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static Task defaultTask = new Task(0, "","");
    public static TasksAdapter taskAdapter;

    ListView lv_tasks;
    private FloatingActionButton fabMain;
    public TaskFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_task, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_tasks = (ListView) view.findViewById(R.id.lv_tasks);
        fabMain = (FloatingActionButton) view.findViewById(R.id.fabMain);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getFragmentManager();
                AddingTaskDialogFragment dialogFragment = new AddingTaskDialogFragment();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        taskAdapter = new TasksAdapter(tasks, getActivity());
        lv_tasks.setAdapter(taskAdapter);
        taskAdapter.setOnItemClick(this);
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(Constant.TASK_FRAGMENT,tasks.get(position).getTitle());
        intent.putExtra(Constant.TASK_FRAGMENT_WORK,tasks.get(position).getWTime());
        PreferenceUtils.setValue(getActivity(),Constant.TASK_SEND,true);
        startActivity(intent);
        getActivity().finish();
    }
}
