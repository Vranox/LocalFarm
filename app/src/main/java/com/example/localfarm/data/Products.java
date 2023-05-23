package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.localfarm.R;

import java.util.ArrayList;
import java.util.List;

public class Products implements Parcelable {
    public String name;
    public int mainPicture;
    public String description;
    public PricePerUnit pricePerUnit;
    private int cumulRating = 0;
    private int nbRating = 0;

    public static List<Products> staticList(){
        List<Products> list = new ArrayList<>();
        list.add(new Products("Tomates", R.drawable.tomate, "1kg",new PricePerUnit(1,QuantityUnits.kg,1)));
        list.add(new Products("Lait", R.drawable.tomate, "description",new PricePerUnit(1,QuantityUnits.L,0.50f)));
        list.add(new Products("Panier de Tomates", R.drawable.tomate, "panier de 5kg",new PricePerUnit(5,QuantityUnits.kg,9)));
        return list;
    }

    public Products(String name, int mainPicture, String description, PricePerUnit pricePerUnit){
        this.name = name;
        this.mainPicture = mainPicture;
        this.description = description;
        this.pricePerUnit = pricePerUnit;
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
    public int getNbRating() {
        return nbRating;
    }
    public int getCumulRating() {
        return cumulRating;
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

    // Functions

    public Products resetRating(){
        cumulRating = nbRating = 0;
        return this;
    }

    public float getRating(){
        if(nbRating==0)return 5;
        return cumulRating/nbRating;
    }

    public Products addRating(int rating){
        cumulRating+=rating;
        nbRating++;
        return this;
    }

    public float priceFor(Quantity quantity){

        return pricePerUnit.priceFor(quantity);
    }
    protected Products(Parcel in) {
        name = in.readString();
        mainPicture = in.readInt();
        description = in.readString();
        pricePerUnit = in.readParcelable(PricePerUnit.class.getClassLoader());
        cumulRating = in.readInt();
        nbRating = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(mainPicture);
        dest.writeString(description);
        dest.writeParcelable(pricePerUnit, flags);
        dest.writeInt(cumulRating);
        dest.writeInt(nbRating);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
