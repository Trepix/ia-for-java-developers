package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Problem implements io.trepix.ia.metaheuristics.Problem<Solution> {
    private final Random generator;
    private final Knapsack emptyKnapsack;
    private final int neighboursNumber;
    private final Items items;

    public Problem(List<Item> items, Configuration configuration) {
        this.items = new Items(new ArrayList<>(items));
        this.generator = configuration.random();
        this.neighboursNumber = configuration.neighboursNumber();
        this.emptyKnapsack = new Knapsack(configuration.maximumKnapsackWeight());
    }

    public Items items() {
        return items.clone();
    }

    @Override
    public Solution randomSolution() {
        var knapsack = emptyKnapsack.clone();
        knapsack.randomFillWith(items(), generator);
        return this.solution(knapsack);
    }

    @Override
    public Solutions<Solution> neighbours(Solution solution) {
        ArrayList<Solution> neighbours = new ArrayList<>();
        var originalKnapsack = solution.knapsack;

        for (int i = 0; i < neighboursNumber; i++) {
            var knapsack = originalKnapsack.clone();
            knapsack.popRandomItem(generator);
            knapsack.randomFillWith(items(), generator);
            neighbours.add(this.solution(knapsack));
        }
        return new Solutions<>(neighbours);
    }

    public Knapsack emptyKnapsack() {
        return this.emptyKnapsack.clone();
    }

    public Solution solution(Knapsack knapsack) {
        return new Solution(knapsack, new Random(generator.nextLong()));
    }

}
