package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.GreedyAlgorithm;

import java.util.List;

import static java.util.Comparator.comparing;

public class KnapsackGreedyAlgorithm extends GreedyAlgorithm<KnapsackProblem> {
    @Override
    protected Solution findSolution(KnapsackProblem problem) {
        var knapsack = problem.emptyKnapsack();
        List<Item> items = problem.items();
        items.sort(comparing(Item::relativeValue).reversed());
        for (Item item : items) {
            if (knapsack.canCarryWith(item)) {
                knapsack.add(item);
            }
        }
        return new KnapsackSolution(knapsack);
    }

}
