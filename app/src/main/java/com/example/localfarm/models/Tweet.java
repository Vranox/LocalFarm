package com.example.localfarm.models;

public class Tweet {

    //private String user;
    private String text;
    //private String date;

    public Tweet(String text){

        this.text = text;

    }

    public Tweet(){
    }



    public String getText(){
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
