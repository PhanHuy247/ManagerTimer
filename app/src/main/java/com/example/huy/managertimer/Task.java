package com.example.huy.managertimer;

import java.util.Date;

/**
 * Created by Laptop88 on 10/10/2016.
 */

public class Task {
    private int color;
    private String title;
    private Date beginDate;
    private int numOfSess;
    private int WTime;
    private double aveWTime;
    private double ratio;

    public Task(int color, String title) {
        this.color = color;
        this.title = title;
//        this.beginDate = beginDate;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
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

    public double getAveWTime() {
        return aveWTime;
    }

    public void setAveWTime(double aveWTime) {
        this.aveWTime = aveWTime;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
