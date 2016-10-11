package com.example.huy.managertimer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.huy.managertimer.Interface.IOnStatisticsClickListener;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.adapter.StatisticsAdapter;
import com.example.huy.managertimer.model.StatisticsItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements IOnStatisticsClickListener {

    ArrayList<StatisticsItem> listStatistic = new ArrayList<>();
    StatisticsAdapter statisticAdapter;

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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem refreshItem = menu.findItem(R.id.delete);
        refreshItem.setVisible(true);
    }

    private void createDataForRecycler() {
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
        listStatistic.add(new StatisticsItem("November 10", "1", "00:12", "2", "02:2"));
    }


    @Override
    public void onClick(StatisticsItem statisticsItem) {

    }
}
