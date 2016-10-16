package com.example.huy.managertimer.model;


/**
 * Created by Huy on 10/10/2016.
 */

public class StatisticsItem {
    private String date;
    private String dayTimeTotal;
    private String taskDetail;

    public StatisticsItem(String date, String dayTimeTotal, String taskDetail) {
        this.date = date;
        this.dayTimeTotal = dayTimeTotal;
        this.taskDetail = taskDetail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayTimeTotal() {
        return dayTimeTotal;
    }

    public void setDayTimeTotal(String dayTimeTotal) {
        this.dayTimeTotal = dayTimeTotal;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }
}