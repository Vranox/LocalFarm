package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Productor extends Client implements Parcelable {
    private String adress;
    private ArrayList<Products> productsAvailable = new ArrayList<>();
    private ArrayList<Command> commandsAvailable = new ArrayList<>();
    private int cumulRating = 0;
    private int nbRatings = 0;

    public Productor(Client client, String adress){
        super(client);
        this.adress = adress;
    }

    protected Productor(Parcel in) {
        super(in);
        adress = in.readString();
        productsAvailable = in.createTypedArrayList(Products.CREATOR);
        cumulRating = in.readInt();
        nbRatings = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(adress);
        dest.writeTypedList(productsAvailable);
        dest.writeInt(cumulRating);
        dest.writeInt(nbRatings);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Productor> CREATOR = new Creator<Productor>() {
        @Override
        public Productor createFromParcel(Parcel in) {
            return new Productor(in);
        }

        @Override
        public Productor[] newArray(int size) {
            return new Productor[size];
        }
    };

    public ArrayList<Products> getProductsAvailable(){
        return productsAvailable;
    }
    private ArrayList<Command> getCommandsAvailable(){
        return commandsAvailable;
    }
    public float getRating(){
        if(nbRatings==0)return 5;
        return cumulRating/nbRatings;
    }
    public float addRating(int ratingReceived){
        cumulRating+=ratingReceived;
        nbRatings++;
        return getRating();
    }

}
