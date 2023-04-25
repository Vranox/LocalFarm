package com.example.localfarm.data;

import com.example.localfarm.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCommand {
    private Products product;
    private Quantity quantity;

    public ProductCommand(Products product, Quantity quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public ProductCommand setQuantity(Quantity value){
        quantity = value;
        return this;
    }

    public Products getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
    }


    public static List<ProductCommand> staticList(){
        ArrayList<ProductCommand> list = new ArrayList<>();
        ProductCommand product1 = new ProductCommand(new Products("Tomate label rouge", R.drawable.tomate,"De magnifique tomates"),new Quantity(2.0, QuantityUnits.kg));
        ProductCommand product2 = new ProductCommand(new Products("Lait Bio",R.drawable.lait,"Lait Ecrémé"),new Quantity(2.0, QuantityUnits.L));
        ProductCommand product3 = new ProductCommand(new Products("Tomate de saison",R.drawable.tomate,"De magnifique tomates"),new Quantity(5.0, QuantityUnits.kg));
        list.add(product1); list.add(product2); list.add(product3);
        return list;
    }

}
