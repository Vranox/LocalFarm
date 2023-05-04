package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Quantity implements Parcelable {
    private double value;
    private QuantityUnits unit;

    public Quantity(double value,QuantityUnits unit){
        this.unit = unit;
        setValue(value);
    }
    public void setValue(double val){ value = (unit != QuantityUnits.unit? val : (int)val); }
    public double getValue(){ return value; }
    public QuantityUnits getUnit(){ return unit; }

    public double convertValue(QuantityUnits unit){
        if(this.unit.ConvertIn(unit) == -1) return value;
        value = value / this.unit.getValue() * unit.getValue();
        this.unit = unit;
        return value;
    }

    @Override
    public String toString(){
        return value+" "+unit;
    }


    protected Quantity(Parcel in) {
        value = in.readDouble();
        unit = QuantityUnits.valueOf(in.readString());
    }

    public static final Creator<Quantity> CREATOR = new Creator<Quantity>() {
        @Override
        public Quantity createFromParcel(Parcel in) {
            return new Quantity(in);
        }

        @Override
        public Quantity[] newArray(int size) {
            return new Quantity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(value);
        dest.writeString(unit.name());
    }
}
