package com.example.huy.managertimer.fragment;


import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huy.managertimer.Interface.IOnClickListenerItemDelete;
import com.example.huy.managertimer.Interface.IOnClickListenerTask;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.activity.MainActivity;
import com.example.huy.managertimer.constant.Constant;
import com.example.huy.managertimer.model.TaskItem;
import com.example.huy.managertimer.adapter.TasksAdapter;
import com.example.huy.managertimer.utilities.PreferenceUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements IOnClickListenerTask, IOnClickListenerItemDelete {
    public static ArrayList<TaskItem> taskItems = new ArrayList<>();
    public static TaskItem defaultTaskItem = new TaskItem(0, "", "");
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
        taskAdapter = new TasksAdapter(taskItems, getActivity());
        lv_tasks.setAdapter(taskAdapter);
        taskAdapter.setOnItemClick(this);
        taskAdapter.setOnItemClickDelete(this);
    }

    @Override
    public void clickItem(int position) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(Constant.TASK_FRAGMENT, taskItems.get(position).getTitle());
        intent.putExtra(Constant.TASK_FRAGMENT_WORK, taskItems.get(position).getWTime());
        PreferenceUtils.setValue(getActivity(), Constant.TASK_SEND, true);
        startActivity(intent);
        getActivity().finish();
        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.tiep_tuc_tien) + taskItems.get(position).getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnclickDelete(final int position) {
        new AlertDialog.Builder(getContext())
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Are you sure delete")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskItems.remove(position);
                        taskAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "You've Cancle Delete", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
