package com.example.localfarm.models;

public class Position {
    public double lat;
    public double lng;
    public String address;

    public Position(double lat, double lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }
}
