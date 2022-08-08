package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Problem;
import io.trepix.ia.knapsack.Solution;
import io.trepix.ia.metaheuristics.algorithms.ParticleSwarm;

public class KnapsackParticleSwarm extends ParticleSwarm<Problem, Solution> {
    protected int iterations = 0;
    private final static int MAX_ITERATIONS = 200;

    public KnapsackParticleSwarm() {
        super();
    }

    @Override
    protected void complete(Solution solution) {
        solution.randomFillWith(problem.items());
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
