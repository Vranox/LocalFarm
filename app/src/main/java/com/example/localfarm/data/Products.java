package com.example.localfarm.data;

public class Products {
    public String name;
    public int mainPicture;
    public String description;
    ;
    private int cumulRating = 0;
    private int nbRating = 0;


    public Products(String name, int mainPicture, String description){
        this.name = name;
        this.mainPicture = mainPicture;
        this.description = description;
    }

    //Getter / Setter
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getMainPicture() {
        return mainPicture;
    }
    public int getNbRating() {
        return nbRating;
    }
    public int getCumulRating() {
        return cumulRating;
    }
    public Products setName(String value) {
        name = value;
        return this;
    }
    public Products setDescription(String value) {
        description = value;
        return this;
    }
    public Products setMainPicture(int value) {
        mainPicture = value;
        return this;
    }

    // Functions

    public Products resetRating(){
        cumulRating = nbRating = 0;
        return this;
    }

    public float getRating(){
        return cumulRating/nbRating;
    }

    public Products addRating(int rating){
        cumulRating+=rating;
        nbRating++;
        return this;
    }
}
