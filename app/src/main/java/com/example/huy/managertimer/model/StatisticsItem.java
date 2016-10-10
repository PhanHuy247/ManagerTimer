package com.example.huy.managertimer.model;


/**
 * Created by Huy on 10/10/2016.
 */

public class StatisticsItem {
    String tvYearMonth;
    String tvNumberWork;
    String tvTimeWork;
    String tvNumberBreak;
    String tvTimeBreak;

    public StatisticsItem(String tvYearMonth, String tvNumberWork, String tvTimeWork, String tvNumberBreak, String tvTimeBreak) {
        this.tvYearMonth = tvYearMonth;
        this.tvNumberWork = tvNumberWork;
        this.tvTimeWork = tvTimeWork;
        this.tvNumberBreak = tvNumberBreak;
        this.tvTimeBreak = tvTimeBreak;
    }

    public String getTvYearMonth() {
        return tvYearMonth;
    }

    public void setTvYearMonth(String tvYearMonth) {
        this.tvYearMonth = tvYearMonth;
    }

    public String getTvNumberWork() {
        return tvNumberWork;
    }

    public void setTvNumberWork(String tvNumberWork) {
        this.tvNumberWork = tvNumberWork;
    }

    public String getTvTimeWork() {
        return tvTimeWork;
    }

    public void setTvTimeWork(String tvTimeWork) {
        this.tvTimeWork = tvTimeWork;
    }

    public String getTvNumberBreak() {
        return tvNumberBreak;
    }

    public void setTvNumberBreak(String tvNumberBreak) {
        this.tvNumberBreak = tvNumberBreak;
    }

    public String getTvTimeBreak() {
        return tvTimeBreak;
    }

    public void setTvTimeBreak(String tvTimeBreak) {
        this.tvTimeBreak = tvTimeBreak;
    }
}
