package com.example.localfarm.models;

import com.google.firebase.database.PropertyName;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class Time implements Comparable<Time>{
    @PropertyName("hour")
    private int hour;
    @PropertyName("minutes")
    private int minutes;

    public Time(int hour, int minutes){
        this.hour = hour;
        this.minutes = minutes;
    }
    public Time(){
        Calendar calendar = Calendar.getInstance();
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
    }

    @PropertyName("hour")
    public int getHour() {
        return hour;
    }

    @PropertyName("minutes")
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(Time otherTime) {
        if (this.hour < otherTime.hour) {
            return -1;
        } else if (this.hour > otherTime.hour) {
            return 1;
        } else {
            if (this.minutes < otherTime.minutes) {
                return -1;
            } else if (this.minutes > otherTime.minutes) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    @Override
    public String toString(){
        return hour + ":" + minutes;
    }
}
