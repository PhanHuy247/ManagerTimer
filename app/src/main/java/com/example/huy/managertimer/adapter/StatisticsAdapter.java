package com.example.huy.managertimer.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huy.managertimer.Interface.IOnStatisticsClickListener;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.model.StatisticsItem;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Huy on 10/10/2016.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.CardViewHolder> {

    private ArrayList<StatisticsItem> statisticsList;
    private Context mContext;

    IOnStatisticsClickListener onItemClickListener;

    public StatisticsAdapter(Context mContext, ArrayList<StatisticsItem> notificationItems) {
        this.statisticsList = notificationItems;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(IOnStatisticsClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_statistics, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final StatisticsItem statistic = statisticsList.get(position);
        holder.tvYearMonth.setText(statisticsList.get(position).getTvYearMonth());
        holder.tvTimeWork.setText(statisticsList.get(position).getTvTimeWork());
        holder.tvTimeBreak.setText(statisticsList.get(position).getTvTimeBreak());
        holder.tvNumberBreak.setText(statisticsList.get(position).getTvNumberBreak());
        holder.tvNumberWork.setText(statisticsList.get(position).getTvNumberWork());
        holder.cvStatisticsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(statistic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statisticsList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvYearMonth;
        TextView tvNumberWork;
        TextView tvTimeWork;
        TextView tvNumberBreak;
        TextView tvTimeBreak;
        CardView cvStatisticsl;

        public CardViewHolder(View itemView) {
            super(itemView);
            tvNumberBreak = (TextView) itemView.findViewById(R.id.tvNumberBreak);
            tvNumberWork = (TextView) itemView.findViewById(R.id.tvNumberWork);
            tvTimeBreak = (TextView) itemView.findViewById(R.id.tvTimeBreak);
            tvTimeWork = (TextView) itemView.findViewById(R.id.tvTimeWork);
            tvYearMonth = (TextView) itemView.findViewById(R.id.tvYearMonthStatistics);
            cvStatisticsl = (CardView) itemView.findViewById(R.id.cvStatistics);
        }
    }
}
