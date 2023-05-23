package com.example.localfarm.models.actor;

import androidx.annotation.NonNull;

import com.example.localfarm.models.actor.Establishment;

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

    @NonNull
    @Override
    public String toString() {
        return "EstablishmentWithDistance{" +
                "establishment=" + establishment +
                ", distance=" + distance +
                '}';
    }
}
