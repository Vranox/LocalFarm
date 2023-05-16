package com.example.localfarm;

import com.example.localfarm.models.Account;
import com.example.localfarm.models.Establishment;
import com.example.myapplication.UserProfile;

public class UserComment {
    private String comment;
    private int numberOfStars;
    private Account user;

    private Establishment establishment;

    public UserComment(Account user, Establishment establishment, String comment, int numberOfStars){
        this.user= user;
        this.establishment = establishment;
        this.comment = comment;
        this.numberOfStars = numberOfStars;
    }
}
