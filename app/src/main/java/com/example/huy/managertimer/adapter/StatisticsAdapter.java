package com.example.huy.managertimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huy.managertimer.Interface.IOnClickListenerTask;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.model.StatisticsItem;

import java.util.ArrayList;

/**
 * Created by Laptop88 on 10/11/2016.
 */

public class StatisticsAdapter extends BaseAdapter{
    ArrayList<StatisticsItem> stats = new ArrayList<>();
    private Context mContext;
    IOnClickListenerTask iOnClickListenerTask;

    public StatisticsAdapter(ArrayList<StatisticsItem> arrayList, Context mContext){
        this.stats = arrayList;
        this.mContext = mContext;
    }
    public void setOnItemClick(IOnClickListenerTask iOnClickListenerTask){
        this.iOnClickListenerTask = iOnClickListenerTask;
    }
    @Override
    public int getCount() {
        return stats.size();
    }

    @Override
    public StatisticsItem getItem(int position) {
        return stats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_statistics, parent, false);
            StatsHolder holder = new StatsHolder();
            holder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
            holder.tv_dayTimeTotal = (TextView) convertView.findViewById(R.id.tv_dayTimeTotal);
            holder.tv_taskDetails = (TextView) convertView.findViewById(R.id.tv_taskDetail);

            convertView.setTag(holder);
        }

        StatsHolder holder = (StatsHolder) convertView.getTag();
        holder.tv_date.setText(stats.get(position).getDate());
        holder.tv_dayTimeTotal.setText(stats.get(position).getDayTimeTotal());
        holder.tv_taskDetails.setText(stats.get(position).getTaskDetail());
        return convertView;
    }


    public static class StatsHolder {
        public TextView tv_date;
        public TextView tv_dayTimeTotal;
        public TextView tv_taskDetails;

    }
}
