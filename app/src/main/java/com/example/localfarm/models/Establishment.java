package com.example.localfarm.models;

public class Establishment {
    public String title;
    public String imageName;
    public String description;
    public Schedule horaires;
    public Position position;

    public Establishment(String title, String description, Schedule horaires) {
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

    public Schedule getHoraires() {
        return horaires;
    }

    public void setHoraires(Schedule horaires) {
        this.horaires = horaires;
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