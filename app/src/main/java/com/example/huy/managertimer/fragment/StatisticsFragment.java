package com.example.huy.managertimer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huy.managertimer.Interface.IOnStatisticsClickListener;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.adapter.StatisticsAdapter;
import com.example.huy.managertimer.model.StatisticsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.statisticmenu, menu);
    }

    private void createDataForRecycler() {
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");

        for(int i = 0; i < 7; i ++) {
            String month_name = month_date.format(calendar.getTime());
            listStatistic.add(new StatisticsItem(month_name + " " + (calendar.get(Calendar.DATE) - i), "1", "00:12", "2", "02:2"));
        }
    }


    @Override
    public void onClick(StatisticsItem statisticsItem) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete){
            Toast.makeText(getContext(),"adsf",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
