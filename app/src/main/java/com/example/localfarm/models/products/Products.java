package com.example.localfarm.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.localfarm.R;

import java.util.ArrayList;
import java.util.List;

public class Products implements Parcelable{
    public String name;
    public int mainPicture;
    public String description;
    public float price;
    public float quantity;
    public QuantityUnits unit;


    protected Products(Parcel in) {
        name = in.readString();
        mainPicture = in.readInt();
        description = in.readString();
        price = in.readFloat();
        quantity = in.readFloat();
        unit = in.readParcelable(QuantityUnits.class.getClassLoader());
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public static List<Products> staticList(){
        List<Products> list = new ArrayList<>();
        list.add(new Products("Tomates", R.drawable.tomate, "1kg",1,2,QuantityUnits.kg));
        list.add(new Products("Lait", R.drawable.lait, "description",6,2.5f,QuantityUnits.L));
        list.add(new Products("Panier de Tomates", R.drawable.tomate, "panier de 5kg",5,4.5f,QuantityUnits.kg));
        return list;
    }

    public Products(String name, int mainPicture, String description, float qty,float price,QuantityUnits unit){
        this.name = name;
        this.mainPicture = mainPicture;
        this.description = description;
        quantity = qty;
        this.price = price;
        this.unit = unit;
    }

    //Getter / Setter
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getMainPicture() {
        return mainPicture;
    }
    public QuantityUnits getUnit(){
        return unit;
    }
    public Products setName(String value) {
        name = value;
        return this;
    }
    public Products setDescription(String value) {
        description = value;
        return this;
    }
    public Products setMainPicture(int value) {
        mainPicture = value;
        return this;
    }

    public float priceFor(float qtt){
        return (qtt/quantity)*price;
    }

    public String pricePerUnit() {
        return String.format("%.2f",this.price/this.quantity)+"/"+this.unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeInt(mainPicture);
        parcel.writeString(description);
        parcel.writeFloat(price);
        parcel.writeFloat(quantity);
        parcel.writeParcelable(unit, i);
    }

}
