package io.trepix.ia.knapsack;

import java.util.Random;

public class Configuration {

    private final double maxKnapsackWeight;

    private final long seed;

    private final int neighboursNumber;


    public Configuration(double maxKnapsackWeight, int maxNeighbours, long seed) {
        this.maxKnapsackWeight = maxKnapsackWeight;
        this.neighboursNumber = maxNeighbours;
        this.seed = seed;
    }

    public double maximumKnapsackWeight() {
        return maxKnapsackWeight;
    }

    public Random random() {
        return new Random(seed);
    }

    public int neighboursNumber() {
        return neighboursNumber;
    }
}
