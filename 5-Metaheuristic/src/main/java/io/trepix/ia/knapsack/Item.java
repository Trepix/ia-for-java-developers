package io.trepix.ia.knapsack;

public class Item {
    private final double weight;
    public final double value;
    private final String name;
    
    public Item(String name , double weight, double value) {
        this.name = name ;
        this.weight = weight;
        this.value = value;
    }
    
    @Override
    public String toString() {
        return name + " (" + weight + ", " + value + ")";
    }

    public double relativeValue() {
        return this.value / this.weight;
    }

    public double weight() {
        return weight;
    }
}
