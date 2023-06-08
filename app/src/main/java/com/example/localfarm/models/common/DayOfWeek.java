package com.example.localfarm.models.common;

public enum DayOfWeek {
    MONDAY("Lundi"),
    TUESDAY("Mardi"),
    WEDNESDAY("Mercredi"),
    THURSDAY("Jeudi"),
    FRIDAY("Vendredi"),
    SATURDAY("Samedi"),
    SUNDAY("Dimanche");

    private final String frenchName;

    DayOfWeek(String frenchName) {
        this.frenchName = frenchName;
    }

    public String getFrenchName() {
        return this.frenchName;
    }
}
