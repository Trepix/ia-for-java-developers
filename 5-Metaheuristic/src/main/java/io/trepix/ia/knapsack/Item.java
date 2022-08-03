package io.trepix.ia.knapsack;

import java.util.Objects;

public record Item(String name, double weight, double value) {

    public double relativeValue() {
        return this.value / this.weight;
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

}
