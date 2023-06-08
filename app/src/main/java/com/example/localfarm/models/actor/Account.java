package com.example.localfarm.models.actor;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.localfarm.models.common.Position;

public class Account implements Parcelable {

    private String email;
    private String password;
    private String phone;
    private String name;
    private String surname;
    private String id;
    private Position approxPosition;
    private int cumulRating = 0;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Position getApproxPosition() {
        return approxPosition;
    }

    public void setApproxPosition(Position approxPosition) {
        this.approxPosition = approxPosition;
    }

    private int nbRatings = 0;

    public Account(String email, String password, String phone, String name, String surname, String id){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.approxPosition = new Position(43.592350475687944, 7.077720662024486, "Antibes");
    }

    public Account(){}

    protected Account(Parcel in) {
        email = in.readString();
        password = in.readString();
        phone = in.readString();
        name = in.readString();
        surname = in.readString();
        id = in.readString();
        cumulRating = in.readInt();
        nbRatings = in.readInt();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public float getRating(){
        if(nbRatings==0)return 5;
        return cumulRating/nbRatings;
    }
    public float addRating(int ratingReceived){
        cumulRating+=ratingReceived;
        nbRatings++;
        return getRating();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(phone);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(id);
        parcel.writeInt(cumulRating);
        parcel.writeInt(nbRatings);
    }
}
