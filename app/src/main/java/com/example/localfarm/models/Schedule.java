package com.example.localfarm.models;

import java.time.LocalTime;

public class Schedule {
    private Time startMorning;
    private Time endMorning;
    private Time startAfternoon;
    private Time endAfternoon;

    public Schedule(Time startMorning, Time endMorning, Time startAfternoon, Time endAfternoon) {
        this.startMorning = startMorning;
        this.endMorning = endMorning;
        this.startAfternoon = startAfternoon;
        this.endAfternoon = endAfternoon;
    }
    public Schedule(){}

    public Time getStartMorning() {
        return startMorning;
    }

    public Time getEndMorning() {
        return endMorning;
    }

    public Time getStartAfternoon() {
        return startAfternoon;
    }

    public Time getEndAfternoon() {
        return endAfternoon;
    }

    public boolean isAvailable(Time time) {
        // Check if the given time falls within the morning schedule
        if (time.compareTo(startMorning)>=0 && time.compareTo(endMorning)<=0) {
            return true;
        }
        // Check if the given time falls within the afternoon schedule
        if (time.compareTo(startAfternoon)>=0 && time.compareTo(endAfternoon)<=0) {
            return true;
        }
        return false;
    }

    public Time getCloseTime(Time time) {
        if(time.compareTo(endMorning)<1){
            return endMorning;
        }
        else {
            return endAfternoon;
        }
    }
}

