package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.algorithms.ParticleSwarm;

public class KnapsackParticleSwarm extends ParticleSwarm<KnapsackProblem, KnapsackSolution> {
    protected int iterations = 0;
    private final static int MAX_ITERATIONS = 200;

    public KnapsackParticleSwarm() {
        super();
    }

    @Override
    protected void complete(KnapsackSolution knapsackSolution) {
        knapsackSolution.randomFillWith(problem.items());
    }

    @Override
    protected boolean meetStopCriteria() {
        return iterations > MAX_ITERATIONS;
    }

    @Override
    protected void updateCriteriaVariables() {
        iterations++;
    }

}
