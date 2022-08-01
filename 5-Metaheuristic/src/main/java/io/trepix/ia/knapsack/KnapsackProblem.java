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
        var knapsack = this.emptyKnapsack.clone();
        Items items = items();
        items.removeWhichCannotBeCarried(knapsack);
        while (knapsack.isNotFull() && items.isNotEmpty()) {
            Item item = items.popRandom(generator);
            knapsack.add(item);
            items.removeWhichCannotBeCarried(knapsack);
        }
        return new KnapsackSolution(knapsack);
    }

    @Override
    public Solutions<KnapsackSolution> neighbours(KnapsackSolution solution) {
        ArrayList<KnapsackSolution> neighbours = new ArrayList<>();
        var originalKnapsack = solution.knapsack;

        for (int i = 0; i < neighboursNumber; i++) {
            var knapsack = originalKnapsack.clone();
            knapsack.popRandomItem(generator);
            var allItems = items();
            allItems.removeUsedItems(knapsack);
            allItems.removeWhichCannotBeCarried(knapsack);

            while (knapsack.isNotFull() && allItems.isNotEmpty()) {
                Item item = allItems.popRandom(generator);
                knapsack.add(item);
                allItems.removeWhichCannotBeCarried(knapsack);
            }
            neighbours.add(new KnapsackSolution(knapsack));
        }
        return new Solutions<>(neighbours);
    }

    public Knapsack emptyKnapsack() {
        return this.emptyKnapsack.clone();
    }

}
