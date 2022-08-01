package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.algorithms.GreedyAlgorithm;

public class KnapsackGreedyAlgorithm extends GreedyAlgorithm<KnapsackProblem, KnapsackSolution> {
    @Override
    protected KnapsackSolution findSolution(KnapsackProblem problem) {
        var knapsack = problem.emptyKnapsack();
        var items = problem.items();
        items.sortByHighestRelativeValue();
        for (Item item : items) {
            if (knapsack.canCarryWith(item)) {
                knapsack.add(item);
            }
        }
        return new KnapsackSolution(knapsack);
    }

}
