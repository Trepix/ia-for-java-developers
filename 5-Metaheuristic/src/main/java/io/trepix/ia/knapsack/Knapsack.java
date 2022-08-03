package io.trepix.ia.knapsack;

import java.util.Random;

public class Knapsack implements Cloneable {

    private final double maximumWeight;
    private Items items;

    public Knapsack(double maximumWeight) {
        this.maximumWeight = maximumWeight;
        items = new Items();
    }

    public boolean canCarryWith(Item item) {
        return items.weight() + item.weight() <= maximumWeight;
    }

    public boolean isNotCarrying(Item searchedItem) {
        for (Item item : items) {
            if (searchedItem.equals(item)) return false;
        }
        return true;
    }

    public boolean isNotFull() {
        return items.weight() < maximumWeight;
    }

    public boolean hasOverweight() {
        return items.weight() > maximumWeight;
    }

    public double weight() {
        return items.weight();
    }

    public double value() {
        return items.value();
    }

    public Item peekRandomItem(Random generator) {
        return items.peekRandom(generator);
    }

    public void popRandomItem(Random generator) {
        items.popRandom(generator);
    }

    public void add(Item item) {
        items.add(item);
    }

    public void randomFillWith(Items allItems, Random generator) {
        allItems.removeUsedItems(this);
        allItems.removeWhichCannotBeCarried(this);
        while (this.isNotFull() && allItems.isNotEmpty()) {
            Item item = allItems.popRandom(generator);
            this.add(item);
            allItems.removeWhichCannotBeCarried(this);
        }
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

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knapsack knapsack = (Knapsack) o;
        return Double.compare(knapsack.maximumWeight, maximumWeight) == 0 && items.equals(knapsack.items);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
