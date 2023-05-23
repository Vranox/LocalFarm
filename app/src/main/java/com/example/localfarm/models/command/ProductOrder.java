package com.example.localfarm.models.command;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.localfarm.R;
import com.example.localfarm.models.products.PricePerUnit;
import com.example.localfarm.models.products.Products;
import com.example.localfarm.models.products.Quantity;
import com.example.localfarm.models.products.QuantityUnits;

import java.util.ArrayList;
import java.util.List;

public class ProductOrder implements Parcelable {
    private Products product;
    private float quantity;

    public ProductOrder(Products product, float quantity){
        this.product = product;
        this.quantity = quantity;
    }

    protected ProductOrder(Parcel in) {
        quantity = in.readFloat();
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

    public ProductOrder setQuantity(float value){
        quantity = value;
        return this;
    }

    public Products getProduct() {
        return product;
    }

    public float getQuantity() {
        return quantity;
    }

    public static List<ProductOrder> staticList(){
        ArrayList<ProductOrder> list = new ArrayList<>();
        list.add(new ProductOrder(new Products("Tomate label rouge", R.drawable.tomate,"De magnifique tomates",3,4.5f,QuantityUnits.kg),5));
        list.add(new ProductOrder(new Products("Lait Bio",R.drawable.lait,"Lait Ecrémé",12,6,QuantityUnits.L),8));
        list.add(new ProductOrder(new Products("Panier de tomates",R.drawable.tomate,"Le BIG panier de 3kg",3,2.5f,QuantityUnits.kg),6));
        return list;
    }

    public String toStringt(){
        return this.product.name+" "+this.quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeFloat(quantity);
    }
}
