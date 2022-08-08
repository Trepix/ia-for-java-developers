package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Problem;
import io.trepix.ia.knapsack.Solution;
import io.trepix.ia.metaheuristics.algorithms.GradientDescent;

public class KnapsackGradientDescent extends GradientDescent<Problem, Solution> {
    protected final static int MAX_ITERATIONS_WITHOUT_IMPROVEMENT = 50;
    protected int iterationsWithoutImprovement = 0;

    @Override
    protected boolean meetStopCriteria() {
        return iterationsWithoutImprovement >= MAX_ITERATIONS_WITHOUT_IMPROVEMENT;
    }

    @Override
    protected void betterSolutionFound(Solution solution) {
        iterationsWithoutImprovement = 0;
    }

    @Override
    protected void updateCriteriaVariables() {
        iterationsWithoutImprovement++;
    }

}
