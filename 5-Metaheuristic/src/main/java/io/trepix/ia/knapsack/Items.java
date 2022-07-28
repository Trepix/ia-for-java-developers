package io.trepix.ia.knapsack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

public class Items implements Iterable<Item> {

    private ArrayList<Item> items;

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

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Item popRandom(Random generator) {
        int index = generator.nextInt(items.size());
        return items.remove(index);
    }
}