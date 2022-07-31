package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.TabuSearch;

public class KnapsackTabuSearch extends TabuSearch<KnapsackProblem> {
    private final static int MAX_ITERATIONS = 100;
    private final static int MAX_ITERATIONS_WITHOUT_IMPROVEMENT = 30;
    private final static int MAX_TABU_SOLUTIONS = 50;
    protected int iterationsWithoutImprovement = 0;
    protected int iterations = 0;

    @Override
    protected boolean meetStopCriteria() {
        return (iterations > MAX_ITERATIONS || iterationsWithoutImprovement > MAX_ITERATIONS_WITHOUT_IMPROVEMENT);
    }

    @Override
    protected void betterSolutionFound(Solution solution) {
        iterationsWithoutImprovement = 0;
    }

    @Override
    protected void updateCriteriaVariables() {
        iterationsWithoutImprovement++;
        iterations++;
    }

    @Override
    protected int maxTabuSolutions() {
        return MAX_TABU_SOLUTIONS;
    }

}
