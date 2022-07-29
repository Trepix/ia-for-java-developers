package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.TabuSearch;

public class KnapsackTabuSearch extends TabuSearch<KnapsackProblem> {
    private final static int NUM_MAX_ITERACIONES = 100;
    private final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 30;
    private final static int NUM_MAX_POSICIONES_TABU = 50;
    protected int iterationsWithoutImprovement = 0;
    protected int iterations = 0;

    @Override
    protected boolean meetStopCriteria() {
        return (iterations > NUM_MAX_ITERACIONES || iterationsWithoutImprovement > NUM_MAX_ITERACIONES_SIN_MEJORA);
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
        return NUM_MAX_POSICIONES_TABU;
    }

}
