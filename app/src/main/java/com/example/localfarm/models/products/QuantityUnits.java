package com.example.localfarm.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum QuantityUnits implements Parcelable {
    // Gramme
    kg(1000f,"%.2f","kg"),
    g(1f,"%.2f","g"),
    mg(0.001f,"%.2f","mg"),
    // Litre
    L(1f,"%.2f","L"),
    cL(0.01f,"%.2f","cL"),
    mL(0.001f,"%.2f","mL"),
    // Unit
    unit(1f,"%.0f","unit");

    static List<QuantityUnits> liter = new ArrayList<>(Arrays.asList(
            QuantityUnits.L,
            QuantityUnits.cL,
            QuantityUnits.mL));
    static List<QuantityUnits> grams = new ArrayList<>(Arrays.asList(
            QuantityUnits.kg,
            QuantityUnits.g,
            QuantityUnits.mg));
    float value;
    String format;
    String displayUnit;
    QuantityUnits(float i,String format, String displayUnit) {
        value=i;
        this.format = format;
        this.displayUnit = displayUnit;
    }


    QuantityUnits(Parcel in) {
        value = in.readFloat();
        format = in.readString();
        displayUnit = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuantityUnits> CREATOR = new Creator<QuantityUnits>() {
        @Override
        public QuantityUnits createFromParcel(Parcel in) {
            return QuantityUnits.values()[in.readInt()];
        }

        @Override
        public QuantityUnits[] newArray(int size) {
            return new QuantityUnits[size];
        }
    };

    float getValue(){
        return value;
    }

    public float getQuantityIn(QuantityUnits other) {
        if(other==this)return 1;
        if(
                (grams.contains(this) && grams.contains(other))
                || (liter.contains(this) && liter.contains(other))
        )return (1 / this.getValue()) * other.getValue();
        return -1;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
