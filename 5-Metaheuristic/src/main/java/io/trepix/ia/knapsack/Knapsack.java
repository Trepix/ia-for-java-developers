package io.trepix.ia.knapsack;

import java.util.LinkedList;
import java.util.List;

public class Knapsack {

    private final double maximumWeight;
    private final Items items;

    public Knapsack(double maximumWeight) {
        this.maximumWeight = maximumWeight;
        items = new Items();
    }

    public List<Item> items() {
        List<Item> list = new LinkedList<>();
        for(Item item : items) {
            list.add(item);
        }
        return list;
    }

    public boolean canCarryWith(Item item) {
        return items.weight() + item.weight() <= maximumWeight;
    }

    public void add(Item item) {
        items.add(item);
    }

    public boolean isFull() {
        return items.weight() == maximumWeight;
    }
}
