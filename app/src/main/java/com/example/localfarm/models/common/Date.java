package com.example.localfarm.models.common;

import java.util.Calendar;

public class Date {
    private int year, month, day;

    public Date(){
        reset();
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

        if (!isValidDate(year, month, day)) reset();
    }

    private void reset(){
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }

    public boolean isValidDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day;
    }
    @Override
    public String toString(){
        return day+"/"+month+"/"+year;
    }
}
