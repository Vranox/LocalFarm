package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.localfarm.R;

import java.util.ArrayList;
import java.util.List;

public class ProductOrder implements Parcelable {
    private Products product;
    private Quantity quantity;

    public ProductOrder(Products product, Quantity quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public ProductOrder setQuantity(Quantity value){
        quantity = value;
        return this;
    }

    public Products getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public static List<ProductOrder> staticList(){
        ArrayList<ProductOrder> list = new ArrayList<>();
        list.add(new ProductOrder(new Products("Tomate label rouge", R.drawable.tomate,"De magnifique tomates"),new Quantity(4.0, QuantityUnits.kg)));
        list.add(new ProductOrder(new Products("Lait Bio",R.drawable.lait,"Lait Ecrémé"),new Quantity(2.0, QuantityUnits.L)));
        list.add(new ProductOrder(new Products("Panier de tomates",R.drawable.tomate,"Le BIG panier de 3kg"),new Quantity(2.0, QuantityUnits.unit)));
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
        dest.writeParcelable(quantity, flags);
    }

    protected ProductOrder(Parcel in) {
        product = in.readParcelable(Products.class.getClassLoader());
        quantity = in.readParcelable(Quantity.class.getClassLoader());
    }

    public static final Creator<ProductOrder> CREATOR = new Creator<ProductOrder>() {
        @Override
        public ProductOrder createFromParcel(Parcel in) {
            return new ProductOrder(in);
        }

        @Override
        public ProductOrder[] newArray(int size) {
            return new ProductOrder[size];
        }
    };
}
