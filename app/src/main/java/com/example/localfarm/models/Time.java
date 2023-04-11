package com.example.localfarm.models;

public class Time implements Comparable<Time>{
    int hour;
    int minutes;
    public Time(int hour, int minutes){
        this.hour = hour;
        this.minutes = minutes;
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
