package com.example.huy.managertimer.model;

/**
 * Created by Laptop88 on 10/10/2016.
 */

public class TaskItem {
    private int color;
    private String title;
    private String beginDate;
    private int numOfSess;
    private int WTime;
    private int aveWTime;
    private double ratio;

    public TaskItem(int WTime, String title, String beginDate) {
        this.WTime = WTime;
        this.title = title;
        this.beginDate = beginDate;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public int getNumOfSess() {
        return numOfSess;
    }

    public void setNumOfSess(int numOfSess) {
        this.numOfSess = numOfSess;
    }

    public int getWTime() {
        return WTime;
    }

    public void setWTime(int WTime) {
        this.WTime = WTime;
    }

    public int getAveWTime() {
        return aveWTime;
    }

    public void setAveWTime(int aveWTime) {
        this.aveWTime = aveWTime;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
