package io.trepix.ia.knapsack;

import java.util.LinkedList;
import java.util.List;

public class Knapsack {

    private final double maximumWeight;

    private double currentWeight = 0;

    private final List<Item> items;

    public Knapsack(double maximumWeight) {
        this.maximumWeight = maximumWeight;
        items = new LinkedList<>();
    }

    public List<Item> items() {
        return items;
    }

    public boolean canCarryWith(Item item) {
        return currentWeight + item.weight() <= maximumWeight;
    }

    public void add(Item item) {
        items.add(item);
        currentWeight += item.weight();
    }
}
