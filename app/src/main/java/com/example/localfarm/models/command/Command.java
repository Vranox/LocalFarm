package com.example.localfarm.models.command;

import android.content.Intent;
import android.provider.CalendarContract;

import com.example.localfarm.models.actor.Account;
import com.example.localfarm.models.common.Time;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Command {
    private static int idCounter=1;
    private int id;
    private Account buyer;
    private Account seller;

    private Time time;
    private Date date;

    public Command(Account buyer, Account seller, Date date, Time meetingTime){
        id=idCounter++;
        this.buyer = buyer;
        this.seller = seller;
        time = meetingTime;
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public Account getSeller() {
        return seller;
    }

    public Account getBuyer() {
        return buyer;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public Intent createMeeting(){

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "recup commande");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "productor wants to know your location");
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        Calendar date = Calendar.getInstance();
        date.set(2023, Calendar.MAY, date.get(Calendar.DAY_OF_MONTH), time.getHour(), time.getMinutes());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTimeInMillis());
        return intent;
    }
}
