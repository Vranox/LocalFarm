package com.example.localfarm.models.actor;

import com.example.localfarm.models.common.Position;
import com.example.localfarm.models.common.Schedule;
import com.example.localfarm.models.products.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Establishment {
    public String title;
    public String imageUri;
    public String description;
    public Map<String, Schedule> horaires;
    List<Products> products;
    public Position position;
    public String id;


    public Position getPosition() {
        return position;
    }

    public Establishment(String title, String description, Map<String,Schedule> horaires, String id) {
        this.title = title;
        this.description = description;
        this.horaires = horaires;
        this.id = id;
        this.products = new ArrayList<>();
    }
    public Establishment(){
        this.horaires = new HashMap<String,Schedule>();
        this.horaires.put("Lundi",new Schedule());
        this.horaires.put("Mardi",new Schedule());
        this.horaires.put("Mercredi",new Schedule());
        this.horaires.put("Jeudi",new Schedule());
        this.horaires.put("Vendredi",new Schedule());
        this.horaires.put("Samedi",new Schedule());
        this.horaires.put("Dimanche",new Schedule());
        this.horaires.get("Samedi").setDayOpen(false);
        this.horaires.get("Dimanche").setDayOpen(false);
    }
    public void setPosition(Position position) {
        this.position = position;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
                ", description='" + description + '\'' +
                ", horaires=" + horaires +
                '}';
    }
}