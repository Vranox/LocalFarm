package com.example.localfarm.models;

import java.util.Map;

public class Establishment {
    public String title;
    public String imageName;
    public String description;
    public Map<String,Schedule> horaires;
    public Position position;

    public Establishment(String title, String description, Map<String,Schedule> horaires) {
        this.title = title;
        this.description = description;
        this.horaires = horaires;
    }
    public Establishment(){
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