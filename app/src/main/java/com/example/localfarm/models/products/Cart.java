package com.example.localfarm.models.products;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Cart extends Observable {
    private List<Products> cartItems = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    // method to add observers
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    // method to notify observers
    @Override
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(this, null);
        }
    }

    public void addProductToCart(Products product){
        System.out.println("Le modèle est notifié d'un ajout et envoie la nouvelle donnée à ses observateurs");
        cartItems.add(product);
        notifyObservers();
    }
}
