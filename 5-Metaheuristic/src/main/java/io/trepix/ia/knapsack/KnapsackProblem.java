package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnapsackProblem implements Problem<KnapsackSolution> {
    private final Random generator;
    private final Knapsack emptyKnapsack;
    private final int neighboursNumber;
    private final Items items;

    public KnapsackProblem(List<Item> items, Configuration configuration) {
        this.items = new Items(new ArrayList<>(items));
        this.generator = configuration.random();
        this.neighboursNumber = configuration.neighboursNumber();
        this.emptyKnapsack = new Knapsack(configuration.maximumKnapsackWeight());
    }

    public Items items() {
        return items.clone();
    }

    @Override
    public KnapsackSolution randomSolution() {
        var knapsack = emptyKnapsack.clone();
        knapsack.randomFillWith(items(), generator);
        return this.solution(knapsack);
    }

    @Override
    public Solutions<KnapsackSolution> neighbours(KnapsackSolution solution) {
        ArrayList<KnapsackSolution> neighbours = new ArrayList<>();
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

    public KnapsackSolution solution(Knapsack knapsack) {
        return new KnapsackSolution(knapsack, new Random(generator.nextLong()));
    }

}
