package com.example.huy.managertimer.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huy.managertimer.Interface.IOnStatisticsClickListener;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.adapter.StatisticsAdapter;
import com.example.huy.managertimer.model.StatisticsItem;
import com.example.huy.managertimer.utilities.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements IOnStatisticsClickListener {

    ArrayList<StatisticsItem> listStatistic = new ArrayList<>();
    StatisticsAdapter statisticAdapter;
    Calendar calendar = Calendar.getInstance();
    RecyclerView rvStatistics;

    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        setupView(view);
        return view;
    }

    private void setupView(View view) {
        createDataForRecycler();
        rvStatistics = (RecyclerView) view.findViewById(R.id.rvStatistics);
        statisticAdapter = new StatisticsAdapter(getContext(), listStatistic);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvStatistics.setLayoutManager(linearLayoutManager);
        rvStatistics.setAdapter(statisticAdapter);
        statisticAdapter.setOnItemClickListener(this);
    }

    private void createDataForRecycler() {
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        for (int i = 0; i < 7; i++) {

            String month_name = month_date.format(calendar.getTime());
            listStatistic.add(new StatisticsItem(month_name + " " + (calendar.get(Calendar.DATE) - i), getActivity().getSharedPreferences(getResources().getString(R.string.setting_pref), Context.MODE_PRIVATE).getInt(calendar.get(Calendar.DATE)+"work",0)+"", "00:12", PreferenceUtils.getValue(getActivity(),calendar.get(Calendar.DATE) + "break",0)+"", "02:2"));
        }

//        if (dayMonthAgo < 7) {
//
//            calendar.add(Calendar.MONTH, -1);
//            SimpleDateFormat format = new SimpleDateFormat("MMMM");
//            String monthAgo = format.format(calendar.getTime());
//
//            for (int i = dayMonthAgo; i < 7; i++) {
//                listStatistic.add(new StatisticsItem(monthAgo + " " + (calendar.get(Calendar.DATE) - i+calendar.get(Calendar.DAY_OF_MONTH)), getActivity().getSharedPreferences(getResources().getString(R.string.setting_pref), Context.MODE_PRIVATE).getInt(calendar.get(Calendar.DATE)+"work",0)+"", "00:12", "2", "02:2"));
//            }
//        }
    }


    @Override
    public void onClick(final StatisticsItem statisticsItem) {
            new AlertDialog.Builder(getContext())
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("Are you sure reset")
                    .setPositiveButton("OK", null)
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            statisticsItem.setTvNumberBreak("0");
                            statisticsItem.setTvNumberWork("0");
                            statisticsItem.setTvTimeBreak("0");
                            statisticsItem.setTvTimeWork("0");
                            statisticAdapter.notifyDataSetChanged();
                            getActivity().getSharedPreferences(getResources().getString(R.string.setting_pref),0).edit().putInt(calendar.get(Calendar.DATE)+"work",0).apply();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(getActivity(), "You've Cancle Reset", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
    }

}
