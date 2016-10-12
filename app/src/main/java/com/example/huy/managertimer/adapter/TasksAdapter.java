package com.example.huy.managertimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huy.managertimer.R;
import com.example.huy.managertimer.Task;

import java.util.ArrayList;

/**
 * Created by Laptop88 on 10/11/2016.
 */

public class TasksAdapter extends BaseAdapter{
    ArrayList<Task> tasks = new ArrayList<>();
    private Context mContext;
    public TasksAdapter(ArrayList<Task> arrayList, Context mContext){
        this.tasks = arrayList;
        this.mContext = mContext;
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
            holder.v_color =  convertView.findViewById(R.id.v_color);
            holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            holder.imb_start = (ImageButton) convertView.findViewById(R.id.imb_startCD);
            holder.imb_stat = (ImageButton) convertView.findViewById(R.id.imb_stat);
            holder.imb_setting = (ImageButton) convertView.findViewById(R.id.imb_setting);
            convertView.setTag(holder);
        }

        TaskHolder holder = (TaskHolder) convertView.getTag();
        holder.v_color.setBackgroundColor(mContext.getResources().getColor(R.color.importanItem));
        holder.tv_title.setText(getItem(position).getTitle());
        setOnButtonClick(holder.imb_start);
        setOnButtonClick(holder.imb_stat);
        setOnButtonClick(holder.imb_setting);
        return convertView;
    }

    private void setOnButtonClick (ImageButton buttonClick){
        buttonClick.setFocusable(false);
        buttonClick.setFocusableInTouchMode(false);
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.imb_startCD:
                        Toast.makeText(mContext, "Start", Toast.LENGTH_SHORT).show();
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
        public View v_color;
        public TextView tv_title;
        public ImageButton imb_start;
        public ImageButton imb_stat;
        public ImageButton imb_setting;
    }
}
