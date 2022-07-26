package io.trepix.ia.knapsack;

import java.util.Random;

public class Configuration {

    private final double maxKnapsackWeight;

    private final Random random;

    private final int neighboursNumber;


    public Configuration(double maxKnapsackWeight, int maxNeighbours, Random random) {
        this.maxKnapsackWeight = maxKnapsackWeight;
        this.random = random;
        this.neighboursNumber = maxNeighbours;
    }

    public double maximumKnapsackWeight() {
        return maxKnapsackWeight;
    }

    public Random random() {
        return random;
    }

    public int neighboursNumber() {
        return neighboursNumber;
    }
}
