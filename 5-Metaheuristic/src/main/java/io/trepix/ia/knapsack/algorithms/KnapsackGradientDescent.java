package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.algorithms.GradientDescent;

public class KnapsackGradientDescent extends GradientDescent<KnapsackProblem, KnapsackSolution> {
    protected final static int MAX_ITERATIONS_WITHOUT_IMPROVEMENT = 50;
    protected int iterationsWithoutImprovement = 0;

    @Override
    protected boolean meetStopCriteria() {
        return iterationsWithoutImprovement >= MAX_ITERATIONS_WITHOUT_IMPROVEMENT;
    }

    @Override
    protected void betterSolutionFound(KnapsackSolution solution) {
        iterationsWithoutImprovement = 0;
    }

    @Override
    protected void updateCriteriaVariables() {
        iterationsWithoutImprovement++;
    }

}
