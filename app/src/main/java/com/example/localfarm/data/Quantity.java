package com.example.localfarm.data;

public class Quantity {
    private double value;
    private QuantityUnits unit;

    public Quantity(double value,QuantityUnits unit){
        this.value = value;
        this.unit = unit;
    }
    public double getValue(){ return value; }
    public QuantityUnits getUnit(){ return unit; }

    public double convertValue(QuantityUnits unit){
        if(this.unit.ConvertIn(unit) == -1) return value;
        value = value / this.unit.getValue() * unit.getValue();
        this.unit = unit;
        return value;
    }

    @Override
    public String toString(){
        return value+" "+unit;
    }
}