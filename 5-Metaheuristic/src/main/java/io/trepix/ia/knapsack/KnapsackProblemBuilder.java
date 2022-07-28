package io.trepix.ia.knapsack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.Math.log10;

public class KnapsackProblemBuilder {

    private static final int NEIGHBOURS_NUMBER = 30;

    private List<Item> items;

    private double maxKnapsackWeight;

    private long seed;
    private int itemsNumber;
    private int maxValue;

    public static KnapsackProblemBuilder withItems(ItemBuilder... item) {
        KnapsackProblemBuilder builder = new KnapsackProblemBuilder();
        builder.items = Stream.of(item).map(ItemBuilder::build).toList();
        return builder;
    }

    public static KnapsackProblemBuilder withRandomItems(long seed) {
        KnapsackProblemBuilder builder = new KnapsackProblemBuilder();
        builder.seed = seed;
        return builder;
    }

    public static KnapsackProblemBuilder withRandomItems() {
        KnapsackProblemBuilder builder = new KnapsackProblemBuilder();
        builder.seed = new Random().nextLong();
        return builder;
    }

    public KnapsackProblemBuilder withNumberOfItems(int numberOfItems) {
        this.itemsNumber = numberOfItems;
        return this;
    }

    public KnapsackProblemBuilder maxItemValue(int value) {
        this.maxValue = value;
        return this;
    }

    public KnapsackProblemBuilder maxKnapsackWeight(double maxWeight) {
        this.maxKnapsackWeight = maxWeight;
        return this;
    }

    public KnapsackProblem build() {
        Random random = new Random(this.seed);
        if (items == null) {
            items = createItems(random);
        }
        Configuration configuration = new Configuration(maxKnapsackWeight, NEIGHBOURS_NUMBER, random);
        return new KnapsackProblem(items, configuration);
    }

    private List<Item> createItems(Random random) {
        List<Item> items = new LinkedList<>();
        for (int i = 1; i <= itemsNumber; i++) {
            String name = leadingZeros(i);
            double weight = random.nextDouble() * maxKnapsackWeight;
            double value = random.nextDouble() * maxValue;
            items.add(new Item(name, weight, value));
        }
        return items;
    }

    private String leadingZeros(int number) {
        int maxZeros = (int) log10(itemsNumber) + 1;
        return String.format("%0" + maxZeros + "d", number);
    }

    public static class ItemBuilder {

        private String name;
        private double weight;
        private double value;

        public static ItemBuilder name(String name) {
            ItemBuilder builder = new ItemBuilder();
            builder.name = name;
            return builder;
        }

        public ItemBuilder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public ItemBuilder value(double value) {
            this.value = value;
            return this;
        }

        public Item build() {
            return new Item(name, weight, value);
        }
    }
}
