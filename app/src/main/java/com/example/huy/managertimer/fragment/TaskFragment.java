package com.example.huy.managertimer.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;
import com.example.huy.managertimer.adapter.TasksAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static Task defaultTask = new Task(0, "");
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

        TasksAdapter taskAdapter = new TasksAdapter(tasks, getActivity());
        lv_tasks.setAdapter(taskAdapter);
    }
}
