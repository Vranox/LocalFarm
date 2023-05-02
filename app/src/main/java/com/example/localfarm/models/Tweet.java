package com.example.localfarm.models;

public class Tweet {

    private String user;
    private String text;

    public Tweet(String user, String text){
        this.user =  user;
        this.text = text;
    }

    public String getUser(){
        return this.user;
    }

    public String getText(){
        return this.text;
    }


}
