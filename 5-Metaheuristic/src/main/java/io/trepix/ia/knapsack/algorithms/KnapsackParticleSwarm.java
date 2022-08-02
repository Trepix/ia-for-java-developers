package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.ParticleSwarm;

import java.util.ArrayList;
import java.util.Random;

public class KnapsackParticleSwarm extends ParticleSwarm<KnapsackProblem, KnapsackSolution> {
    private final Random generator;
    protected int iterations = 0;
    private final static int MAX_ITERATIONS = 200;

    public KnapsackParticleSwarm(Random generator) {
        super();
        this.generator = generator;
    }

    @Override
    protected void updateSolutions() {
        ArrayList<KnapsackSolution> newSolutions = new ArrayList<>();
        for (KnapsackSolution sol : solutions) {
            KnapsackSolution solution = sol;
            if (!solution.equals(bestSolution)) {
                solution.improveWith(bestSolution, generator);
                solution.improveWith(bestCurrentSolution, generator);
                solution = complete(solution);
            }
            newSolutions.add(solution);
        }
        solutions = newSolutions;
    }

    protected KnapsackSolution complete(KnapsackSolution knapsackSolution) {
        var knapsack = knapsackSolution.knapsack();
        knapsack.randomFillWith(problem.items(), generator);
        return problem.solution(knapsack);
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
