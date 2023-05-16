package com.example.localfarm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.localfarm.R;

import java.util.ArrayList;

public class Client implements Parcelable {
    protected int profilePicture = R.drawable.default_profile_image;
    protected String name;
    protected String surname;
    protected ArrayList<Command> commands = new ArrayList<>();

    public Client(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public Client(Client client) {
        name = client.name;
        surname = client.surname;
        commands = client.commands;
    }

    protected Client(Parcel in) {
        profilePicture = in.readInt();
        name = in.readString();
        surname = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public int getProfilePicture(){return profilePicture;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(profilePicture);
        parcel.writeString(name);
        parcel.writeString(surname);
    }
}
