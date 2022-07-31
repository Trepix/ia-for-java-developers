package io.trepix.ia.knapsack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

public class Items implements Iterable<Item>, Cloneable {

    private ArrayList<Item> items;

    Items() {
        this.items = new ArrayList<>();
    }

    Items(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    public void sortByHighestRelativeValue() {
        items.sort(comparing(Item::relativeValue).reversed());
    }

    public void removeWhichCannotBeCarried(Knapsack knapsack) {
        items = items.stream()
                .filter(knapsack::canCarryWith)
                .collect(toCollection(ArrayList::new));
    }

    public boolean isNotEmpty() {
        return !items.isEmpty();
    }

    public Item popRandom(Random generator) {
        int index = generator.nextInt(items.size());
        return items.remove(index);
    }

    public double weight() {
        return items.stream().map(Item::weight).reduce(0.0, Double::sum);
    }

    public void add(Item item) {
        this.items.add(item);
    }

    @Override
    public Items clone() {
        try {
            Items clone = (Items) super.clone();
            clone.items = new ArrayList<>(this.items);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void removeUsedItems(Knapsack knapsack) {
        Items knapsackItems = knapsack._items();
        items.removeAll(knapsackItems.items);
    }
}