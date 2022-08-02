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

    public double maximumWeight() {
        return maximumWeight;
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

    public boolean isCarrying(Item searchedItem) {
        for (Item item: items) {
            if (searchedItem.equals(item)) return true;
        }
        return false;
    }

    public void add(Item item) {
        items.add(item);
    }

    public boolean isNotFull() {
        return items.weight() < maximumWeight;
    }


    public void popRandomItem(Random generator) {
        items.popRandom(generator);
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

    public double weight() {
        return items.weight();
    }

    public double value() {
        return items.value();
    }
}
