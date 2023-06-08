package com.example.localfarm.factory;

public class ConcreteTweet implements Tweet {
    //private String user;
    private String text;
    //private String date;

    public ConcreteTweet(String text){

        this.text = text;

    }

    public ConcreteTweet(){
    }



    public String getText(){
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void display() {
        System.out.println("Contenu du tweet : "+this.text);
    }
}
