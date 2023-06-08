package com.example.localfarm.models.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum QuantityUnits {
    // Gramme
    kg(1000f),
    g(1f),
    mg(0.001f),
    // Litre
    L(1f),
    cL(0.01f),
    mL(0.001f),
    // Unit
    unit(1f);

    static List<QuantityUnits> liter = new ArrayList<>(Arrays.asList(
            QuantityUnits.L,
            QuantityUnits.cL,
            QuantityUnits.mL));
    static List<QuantityUnits> grams = new ArrayList<>(Arrays.asList(
            QuantityUnits.kg,
            QuantityUnits.g,
            QuantityUnits.mg));
    float value;
    QuantityUnits(float i) {
        value=i;
    }
    float getValue(){
        return value;
    }

    public float ConvertIn(QuantityUnits other) {
        if(other==this)return 1;
        if(
                (grams.contains(this) && grams.contains(other))
                || (liter.contains(this) && liter.contains(other))
        )return (1 / this.getValue()) * other.getValue();
        return -1;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
