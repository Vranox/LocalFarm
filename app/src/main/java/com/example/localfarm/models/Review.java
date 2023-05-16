package com.example.localfarm.models;

public class Review {
    private String comment;
    private int numberOfStars;
    private Account user;

    public Review(Account user, Establishment establishment, String comment, int numberOfStars){
        this.user= user;
        this.comment = comment;
        this.numberOfStars = numberOfStars;
    }
}
