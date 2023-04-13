package com.example.localfarm.models;

import com.google.firebase.database.PropertyName;

public class Time implements Comparable<Time>{
    @PropertyName("hour")
    private int hour;
    @PropertyName("minutes")
    private int minutes;

    public Time(int hour, int minutes){
        this.hour = hour;
        this.minutes = minutes;
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
}
