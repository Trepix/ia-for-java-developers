package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Problem;
import io.trepix.ia.knapsack.Solution;
import io.trepix.ia.metaheuristics.algorithms.SimulatedAnnealing;

import java.util.Random;

public class KnapsackSimulatedAnnealing extends SimulatedAnnealing<Problem, Solution> {
    private final static int MAX_ITERATIONS = 100;
    private final static int MAX_ITERATIONS_WITHOUT_IMPROVEMENT = 30;
    public static final double DECREASING_RATIO = 0.95;
    protected int iterationsWithoutImprovement = 0;
    protected int iterations = 0;

    public KnapsackSimulatedAnnealing(Random generator) {
        super(generator);
    }

    protected double decreaseTemperature(double temperature) {
        return temperature * DECREASING_RATIO;
    }

    @Override
    protected double initialTemperature() {
        return 5;
    }

    @Override
    protected boolean meetStopCriteria() {
        return iterations > MAX_ITERATIONS || iterationsWithoutImprovement > MAX_ITERATIONS_WITHOUT_IMPROVEMENT;
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

}
