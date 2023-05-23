package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PricePerUnit extends Quantity implements Parcelable {
    private float price;
    public PricePerUnit(float qtt,QuantityUnits unit, float price ){
        super(qtt,unit);
        this.price = price;
    }

    public float priceFor(Quantity quantity) {
        float ratio = this.unit.ConvertIn(quantity.unit);
        if(ratio==-1)return this.price;
        return price*ratio*(quantity.value/this.value);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(price);
    }

    protected PricePerUnit(Parcel in) {
        super(in);
        price = in.readFloat();
    }

    public static final Parcelable.Creator<PricePerUnit> CREATOR = new Parcelable.Creator<PricePerUnit>() {
        @Override
        public PricePerUnit createFromParcel(Parcel in) {
            return new PricePerUnit(in);
        }

        @Override
        public PricePerUnit[] newArray(int size) {
            return new PricePerUnit[size];
        }
    };

    @Override
    public String toString(){
        return (this.price+"€/"+this.value+""+this.unit);
    }



}
