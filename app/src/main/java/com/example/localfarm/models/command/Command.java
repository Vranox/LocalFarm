package com.example.localfarm.models.command;

import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;

import androidx.annotation.NonNull;

import com.example.localfarm.models.actor.Account;
import com.example.localfarm.models.common.Date;
import com.example.localfarm.models.common.Time;
import com.example.localfarm.order.Order;
import com.example.localfarm.order.OrderState;
import com.example.localfarm.utils.NotifHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Command implements Parcelable {
    private static int idCounter=1;
    private int id;
    private Account buyer;
    private Account seller;
    private Time time;
    private Date date;
    private CommandStatus status = CommandStatus.Waiting;
    private List<ProductOrder> productList = new ArrayList<>();
    public Command(Account buyer, Account seller, Date date, Time time){
        System.out.println("########### Command ############");
        id=idCounter++;
        this.buyer = buyer;
        this.seller = seller;
        this.time = time;
        this.date = date;
        System.out.println(getDate());
    }


    protected Command(Parcel in) {
        id = in.readInt();
        buyer = in.readParcelable(Account.class.getClassLoader());
        seller = in.readParcelable(Account.class.getClassLoader());
        time = in.readParcelable(Time.class.getClassLoader());
        date = in.readParcelable(Date.class.getClassLoader());
        productList = in.createTypedArrayList(ProductOrder.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(buyer, flags);
        dest.writeParcelable(seller, flags);
        dest.writeParcelable(time, flags);
        dest.writeParcelable(date, flags);
        dest.writeTypedList(productList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Command> CREATOR = new Creator<Command>() {
        @Override
        public Command createFromParcel(Parcel in) {
            return new Command(in);
        }

        @Override
        public Command[] newArray(int size) {
            return new Command[size];
        }
    };

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

    public void setStatus(CommandStatus status) {
        if(getStatus() == status
        || getStatus() == CommandStatus.Canceled
        || getStatus() == CommandStatus.Completed
        ) return;
        this.status = status;
    }
    public CommandStatus getStatus() {
        return status;
    }

    public Intent createMeeting(){

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Recuperer votre commande!");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "productor wants to know your location");
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

// Créer une instance de Calendar et définir les valeurs
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
        calendar.set(Calendar.MINUTE, time.getMinutes());

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis()+3600000);
        return intent;
    }

    public void setList(List<ProductOrder> list){
        productList = list;
    }

    public void removeProduct(ProductOrder order){
        productList.remove(order);
    }
    public void addProduct(ProductOrder order){
        productList.add(order);
    }
    public void addProduct(List<ProductOrder> order){
        productList.addAll(order);
    }

    public List<ProductOrder> getProductList() {
        return productList;
    }
    public String getStringDate(){
        return date.toString();
    }


    public float getTotalPrice(){
        float price = 0;
        for (ProductOrder order : productList) {
            price += order.getPrice();
        }
        return price;
    }


}
