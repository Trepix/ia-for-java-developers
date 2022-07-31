package io.trepix.ia.knapsack;

public class Item {
    public final double value;
    private final double weight;
    private final String name;

    public Item(String name, double weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    @Override
    public String toString() {
        return name + " (" + weight + ", " + value + ")";
    }

    public double value() {
        return this.value;
    }

    public double relativeValue() {
        return this.value / this.weight;
    }

    public double weight() {
        return weight;
    }
}
