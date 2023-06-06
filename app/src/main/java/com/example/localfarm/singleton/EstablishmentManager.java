package com.example.localfarm.singleton;

import com.example.localfarm.models.actor.EstablishmentWithDistance;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentManager {
    private static EstablishmentManager instance;
    private List<EstablishmentWithDistance> establishments;

    private EstablishmentManager() {
        establishments = new ArrayList<>();
    }

    public static synchronized EstablishmentManager getInstance() {
        if (instance == null) {
            instance = new EstablishmentManager();
        }
        return instance;
    }

    public List<EstablishmentWithDistance> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<EstablishmentWithDistance> establishments) {
        this.establishments = establishments;
    }
}
