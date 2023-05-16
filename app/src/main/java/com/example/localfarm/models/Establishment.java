package com.example.localfarm.models;

import java.util.Map;

public class Establishment {
    public String title;
    public String imageName;
    public String description;
    public Map<String,Schedule> horaires;
    public Position position;
    public String id;

    public Establishment(String title, String description, Map<String,Schedule> horaires, String id) {
        this.title = title;
        this.description = description;
        this.horaires = horaires;
        this.id = id;
    }
    public Establishment(){
    }

    public String getId_owner() { return this.id;}

    public void setId_owner(String id_owner) {
        this.id = id_owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Schedule getHoraires(String dayOfWeek) {
        return horaires.get(dayOfWeek);
    }

    public void setHoraires(String dayOfWeek,Schedule horaires) {
        this.horaires.put(dayOfWeek,horaires);
    }
    @Override
    public String toString() {
        return "Establishment{" +
                "title='" + title + '\'' +
                ", imageName='" + imageName + '\'' +
                ", description='" + description + '\'' +
                ", horaires=" + horaires +
                '}';
    }
}