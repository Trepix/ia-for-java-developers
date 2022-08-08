package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.Problem;
import io.trepix.ia.knapsack.Solution;
import io.trepix.ia.metaheuristics.algorithms.GreedyAlgorithm;

public class KnapsackGreedyAlgorithm extends GreedyAlgorithm<Problem, Solution> {
    @Override
    protected Solution findSolution(Problem problem) {
        var knapsack = problem.emptyKnapsack();
        var items = problem.items();
        items.sortByHighestRelativeValue();
        for (Item item : items) {
            if (knapsack.canCarryWith(item)) {
                knapsack.add(item);
            }
        }
        return problem.solution(knapsack);
    }

}
