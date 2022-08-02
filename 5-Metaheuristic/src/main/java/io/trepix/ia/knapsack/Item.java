package io.trepix.ia.knapsack;

import java.util.Objects;

public class Item {
    public final double value;
    private final double weight;
    private final String name;

    public Item(String name, double weight, double value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
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

    @Override
    public String toString() {
        return name + " (" + weight + ", " + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.value, value) == 0 && Double.compare(item.weight, weight) == 0 && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, weight, name);
    }
}
