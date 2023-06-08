package com.example.localfarm.ui.EstablishmentCreation;

import com.example.localfarm.models.actor.Establishment;

public interface OnDataChangeListener {
    void onDataChanged(Object newData);
    Establishment getEstablishment();
}
