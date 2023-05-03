package com.example.localfarm.models;

public class Tweet {

    private String user;
    private String text;
    private String date;

    public Tweet(String user, String text, String date){
        this.user =  user;
        this.text = text;
        this.date = date;
    }

    public Tweet(){
    }

    public String getUser(){
        return this.user;
    }

    public String getText(){
        return this.text;
    }

    public String getDate() { return this.date;}


}
