package com.example.huy.managertimer.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huy.managertimer.Interface.IOnStatisticsClickListener;
import com.example.huy.managertimer.R;
import com.example.huy.managertimer.adapter.StatisticsAdapter;
import com.example.huy.managertimer.model.StatisticsItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {
    ListView lv_dateStat;
    private ArrayList<StatisticsItem> arrayList = new ArrayList<>();
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

    @Override
    public void onResume() {
        super.onResume();
        getDateStat();
        StatisticsAdapter adapter = new StatisticsAdapter(arrayList, getActivity());
        lv_dateStat.setAdapter(adapter);
        lv_dateStat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    private void getDateStat() {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.dateFormat1));

        for (int i = 0; i < 7; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            Date todate = cal.getTime();
            String date = dateFormat.format(todate);
            SharedPreferences datePrefs = getActivity().getSharedPreferences(date, Context.MODE_PRIVATE);
            Set<String> set = datePrefs.getStringSet(getString(R.string.setTaskTitle), new HashSet<String>());
            String taskDetail = "";
            int totalWTime = 0;
            for (String ins : set) {
                int wTime = datePrefs.getInt(ins, 0);
                totalWTime += wTime;
                taskDetail += (ins+":\t"+wTime+" (min)\n");
            }

            arrayList.add(new StatisticsItem(date, ""+totalWTime, taskDetail));
        }
    }

    private void setupView(View view) {
        lv_dateStat = (ListView) view.findViewById(R.id.lv_stat);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}