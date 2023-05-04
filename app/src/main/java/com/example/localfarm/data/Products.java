package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Products implements Parcelable {
    public String name;
    public int mainPicture;
    public String description;
    private int cumulRating = 0;
    private int nbRating = 0;


    public Products(String name, int mainPicture, String description){
        this.name = name;
        this.mainPicture = mainPicture;
        this.description = description;
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
        return cumulRating/nbRating;
    }

    public Products addRating(int rating){
        cumulRating+=rating;
        nbRating++;
        return this;
    }

    protected Products(Parcel in) {
        name = in.readString();
        mainPicture = in.readInt();
        description = in.readString();
        cumulRating = in.readInt();
        nbRating = in.readInt();
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(mainPicture);
        parcel.writeString(description);
        parcel.writeInt(cumulRating);
        parcel.writeInt(nbRating);
    }
}
