package com.example.localfarm.models.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum QuantityUnits {
    // Gramme
    kg(1000f,"%.2f"),
    g(1f,"%.2f"),
    mg(0.001f,"%.2f"),
    // Litre
    L(1f,"%.2f"),
    cL(0.01f,"%.2f"),
    mL(0.001f,"%.2f"),
    // Unit
    unit(1f,"%.0f");

    static List<QuantityUnits> liter = new ArrayList<>(Arrays.asList(
            QuantityUnits.L,
            QuantityUnits.cL,
            QuantityUnits.mL));
    static List<QuantityUnits> grams = new ArrayList<>(Arrays.asList(
            QuantityUnits.kg,
            QuantityUnits.g,
            QuantityUnits.mg));
    float value;
    String format;
    QuantityUnits(float i,String format) {
        value=i;
        this.format = format;
    }
    float getValue(){
        return value;
    }

    public float getQuantityIn(QuantityUnits other) {
        if(other==this)return 1;
        if(
                (grams.contains(this) && grams.contains(other))
                || (liter.contains(this) && liter.contains(other))
        )return (1 / this.getValue()) * other.getValue();
        return -1;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
