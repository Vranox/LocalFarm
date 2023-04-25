package com.example.localfarm.data;

public class Productor extends Client{
    private Store store;
    private int cumulRating = 0;
    private int nbRatings = 0;

    public Productor(Client client, String adress){
        super(client);
        store = new Store(adress);
    }

}
