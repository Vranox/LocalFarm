package com.example.localfarm.models;

public class EstablishmentWithDistance {
    public Establishment establishment;
    public float distance;

    public EstablishmentWithDistance(Establishment establishment, float distance) {
        this.establishment = establishment;
        this.distance = distance;
    }

    public Establishment getEstablishment() {
        return establishment;
    }
}
