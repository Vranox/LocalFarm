package com.example.localfarm.models;

public class Schedule {
    private Time startMorning;
    private Time endMorning;
    private Time startAfternoon;
    private Time endAfternoon;
    private boolean isDayOpen;
    private boolean isMorningOpen;
    private boolean isAfternoonOpen;

    public Schedule(Time startMorning, Time endMorning, Time startAfternoon, Time endAfternoon) {
        this.startMorning = startMorning;
        this.endMorning = endMorning;
        this.startAfternoon = startAfternoon;
        this.endAfternoon = endAfternoon;
        isDayOpen = true;
        isMorningOpen = true;
        isAfternoonOpen = true;
    }
    public Schedule(){
        this.startMorning = new Time(8,0);
        this.endMorning = new Time(12,0);
        this.startAfternoon = new Time(13,0);
        this.endAfternoon = new Time(17,0);
        isDayOpen = true;
        isMorningOpen = true;
        isAfternoonOpen = true;
    }

    public void setStartMorning(Time startMorning) {
        this.startMorning = startMorning;
    }

    public void setEndMorning(Time endMorning) {
        this.endMorning = endMorning;
    }

    public void setStartAfternoon(Time startAfternoon) {
        this.startAfternoon = startAfternoon;
    }

    public void setEndAfternoon(Time endAfternoon) {
        this.endAfternoon = endAfternoon;
    }

    public boolean isDayOpen() {
        return isDayOpen;
    }

    public void setDayOpen(boolean dayOpen) {
        isDayOpen = dayOpen;
    }

    public boolean isMorningOpen() {
        return isMorningOpen;
    }

    public void setMorningOpen(boolean morningOpen) {
        isMorningOpen = morningOpen;
    }

    public boolean isAfternoonOpen() {
        return isAfternoonOpen;
    }

    public void setAfternoonOpen(boolean afternoonOpen) {
        isAfternoonOpen = afternoonOpen;
    }

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
    @Override
    public String toString() {
        return "Schedule{" +
                "startMorning=" + startMorning +
                ", endMorning=" + endMorning +
                ", startAfternoon=" + startAfternoon +
                ", endAfternoon=" + endAfternoon +
                '}';
    }

    public String getOpenTime(Time timeOfNow) {
        if((timeOfNow.compareTo(startMorning)<1)||(timeOfNow.compareTo(endAfternoon)>-1)){
            return startMorning.toString();
        }
        else{
            return startAfternoon.toString();
        }
    }
}

