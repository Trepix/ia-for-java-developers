package io.trepix.ia.knapsack;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

public class Items implements Iterable<Item>, Cloneable {

    private ArrayList<Item> list;

    Items() {
        this.list = new ArrayList<>();
    }

    Items(ArrayList<Item> items) {
        this.list = items;
    }

    public void sortByHighestRelativeValue() {
        list.sort(comparing(Item::relativeValue).reversed());
    }

    public void removeWhichCannotBeCarried(Knapsack knapsack) {
        list = list.stream()
                .filter(knapsack::canCarryWith)
                .collect(toCollection(ArrayList::new));
    }

    public boolean isNotEmpty() {
        return !list.isEmpty();
    }

    public Item popRandom(Random generator) {
        int index = generator.nextInt(list.size());
        return list.remove(index);
    }

    public Item peekRandom(Random generator) {
        int index = generator.nextInt(list.size());
        return list.get(index);
    }

    public double weight() {
        return list.stream().map(Item::weight).reduce(0.0, Double::sum);
    }

    public void add(Item item) {
        this.list.add(item);
    }

    public void removeUsedItems(Knapsack knapsack) {
        list = list.stream()
                .filter(knapsack::isNotCarrying)
                .collect(toCollection(ArrayList::new));

    }
    public double value() {
        return list.stream().map(Item::value).reduce(0.0, Double::sum);
    }

    @Override
    public Iterator<Item> iterator() {
        return list.iterator();
    }

    @Override
    public Items clone() {
        try {
            Items clone = (Items) super.clone();
            clone.list = new ArrayList<>(this.list);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" - ");
        for (Item item : list) {
            sj.add(item.toString());
        }
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        if (items.list.size() != this.list.size()) return false;
        return new HashSet<>(this.list).equals(new HashSet<>(items.list));
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}