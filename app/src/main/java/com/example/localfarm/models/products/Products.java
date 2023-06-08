package com.example.localfarm.models.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.localfarm.R;

import java.util.ArrayList;
import java.util.List;

public class Products {
    public String name;
    public String mainPicture;
    public String description;
    public float price;
    public float quantity;
    public QuantityUnits unit;

    public static List<Products> staticList(){
        List<Products> list = new ArrayList<>();
        list.add(new Products("Tomates", "https://firebasestorage.googleapis.com/v0/b/localfarm-a7781.appspot.com/o/images%2Ftomate.png?alt=media&token=3cb3524c-a0d7-4405-bcf4-183512d9f219", "1kg",1,2,QuantityUnits.kg));
        list.add(new Products("Lait", "https://firebasestorage.googleapis.com/v0/b/localfarm-a7781.appspot.com/o/images%2Flait.png?alt=media&token=a1c36ba4-b6ca-4157-b58b-9d01bc506e1f", "description",6,2.5f,QuantityUnits.L));
        list.add(new Products("Panier de Tomates", "https://firebasestorage.googleapis.com/v0/b/localfarm-a7781.appspot.com/o/images%2Ftomate.png?alt=media&token=3cb3524c-a0d7-4405-bcf4-183512d9f219", "panier de 5kg",5,4.5f,QuantityUnits.kg));
        return list;
    }

    public Products(String name, String mainPicture, String description, float qty,float price,QuantityUnits unit){
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
    public String getMainPicture() {
        return mainPicture;
    }
    public Products setName(String value) {
        name = value;
        return this;
    }
    public Products setDescription(String value) {
        description = value;
        return this;
    }
    public Products setMainPicture(String value) {
        mainPicture = value;
        return this;
    }

    public float priceFor(float qtt){
        return (qtt/quantity)*price;
    }

    public String pricePerUnit() {
        return String.format("%.2f",this.price/this.quantity)+"/"+this.unit;
    }
}
