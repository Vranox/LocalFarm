package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Quantity implements Parcelable {
    protected float value;
    protected QuantityUnits unit;

    public Quantity(float value,QuantityUnits unit){
        this.unit = unit;
        setValue(value);
    }
    public void setValue(float val){ value = (unit == QuantityUnits.unit? (int)val : val); }
    public float getValue(){ return value; }
    public QuantityUnits getUnit(){ return unit; }

    public float convertValue(QuantityUnits unit){
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
        value = in.readFloat();
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
        dest.writeFloat(value);
        dest.writeString(unit.name());
    }
}
