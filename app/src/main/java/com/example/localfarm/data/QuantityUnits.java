package com.example.localfarm.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum QuantityUnits {
    // Gramme
    kg(1000),
    g(1),
    mg(0.001),
    // Litre
    L(1),
    cL(0.01),
    mL(0.001),
    // Unit
    unit(1);

    static List<QuantityUnits> liter = new ArrayList<>(Arrays.asList(
            QuantityUnits.L,
            QuantityUnits.cL,
            QuantityUnits.mL));
    static List<QuantityUnits> grams = new ArrayList<>(Arrays.asList(
            QuantityUnits.kg,
            QuantityUnits.g,
            QuantityUnits.mg));
    double value;
    QuantityUnits(double i) {
        value=i;
    }
    double getValue(){
        return value;
    }

    public double ConvertIn(QuantityUnits unit) {
        if(
                (grams.contains(this) && grams.contains(unit))
                || (liter.contains(this) && liter.contains(unit))
        )return -1;
        return (1 / this.getValue()) * unit.getValue();
    }
}
