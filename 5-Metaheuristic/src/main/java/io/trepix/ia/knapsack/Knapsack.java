package io.trepix.ia.knapsack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Knapsack implements Cloneable {

    private final double maximumWeight;
    private Items items;

    public Knapsack(double maximumWeight) {
        this.maximumWeight = maximumWeight;
        items = new Items();
    }

    @Override
    public Knapsack clone() {
        try {
            Knapsack clone = (Knapsack) super.clone();
            clone.items = items.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Items _items() {
        return this.items;
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


    public void popRandomItem(Random generator) {
        items.popRandom(generator);
    }
}
