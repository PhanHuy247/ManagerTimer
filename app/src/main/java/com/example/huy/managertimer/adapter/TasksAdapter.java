package com.example.huy.managertimer.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huy.managertimer.Interface.IOnClickListenerTask;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.model.Task;
import com.example.huy.managertimer.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Laptop88 on 10/11/2016.
 */

public class TasksAdapter extends BaseAdapter{
    ArrayList<Task> tasks = new ArrayList<>();
    private Context mContext;
    IOnClickListenerTask iOnClickListenerTask;

    public TasksAdapter(ArrayList<Task> arrayList, Context mContext){
        this.tasks = arrayList;
        this.mContext = mContext;
    }
    public void setOnItemClick(IOnClickListenerTask iOnClickListenerTask){
        this.iOnClickListenerTask = iOnClickListenerTask;
    }
    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_task, parent, false);
            TaskHolder holder = new TaskHolder();
            holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            holder.imb_start = (ImageButton) convertView.findViewById(R.id.imb_start);
            holder.imb_stat = (ImageButton) convertView.findViewById(R.id.imb_stat);
            holder.imb_setting = (ImageButton) convertView.findViewById(R.id.imb_setting);
            convertView.setTag(holder);
        }

        TaskHolder holder = (TaskHolder) convertView.getTag();
        holder.tv_title.setText(getItem(position).getTitle());
        setOnButtonClick(holder.imb_start, position);
        setOnButtonClick(holder.imb_stat, position);
        setOnButtonClick(holder.imb_setting, position);
        return convertView;
    }

    private void setOnButtonClick (ImageButton buttonClick, final int postition){
        buttonClick.setFocusable(false);
        buttonClick.setFocusableInTouchMode(false);
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.imb_start:
//                        Intent intent = new Intent(mContext, MainActivity.class);
//                        intent.putExtra("position", postition);
//                        mContext.startActivity(intent);
                        iOnClickListenerTask.clickItem(postition);
                        break;
                    case R.id.imb_stat:
                        Toast.makeText(mContext, "Statistic", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.imb_setting:
                        Toast.makeText(mContext, "Setting", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    public static class TaskHolder{
        public TextView tv_title;
        public ImageButton imb_start;
        public ImageButton imb_stat;
        public ImageButton imb_setting;
    }
}
