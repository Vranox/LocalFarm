package com.example.localfarm.models;

public class Establishment {
    public String title;
    public String imageName;
    public String description;
    public Schedule horaires;

    public Establishment(String title, String description, Schedule horaires) {
        this.title = title;
        this.description = description;
        this.horaires = horaires;
    }
}