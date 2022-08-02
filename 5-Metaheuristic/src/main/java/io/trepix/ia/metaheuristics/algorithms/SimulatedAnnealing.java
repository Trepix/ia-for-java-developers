package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.Random;

public abstract class SimulatedAnnealing<P extends Problem<S>, S extends Solution<S>> extends Algorithm<P, S> {

    private final Random generator;
    protected S currentSolution;
    protected S bestSolution;
    protected double temperature;

    public SimulatedAnnealing(Random generator) {
        super("Simulated Annealing");
        this.generator = generator;
    }

    @Override
    public final S solve(P problem) {
        temperature = initialTemperature();
        currentSolution = problem.randomSolution();
        bestSolution = currentSolution;

        while (!meetStopCriteria()) {
            problem.neighbours(currentSolution)
                    .best()
                    .ifPresent(solution -> {
                        if (solution.isBetterThan(currentSolution) || isNotTooWorse(solution)) {
                            currentSolution = solution;
                            if (solution.isBetterThan(bestSolution)) {
                                bestSolution = solution;
                                betterSolutionFound(bestSolution);
                            }
                        }
                    });

            updateCriteriaVariables();
            temperature = decreaseTemperature(temperature);
        }
        return bestSolution;
    }

    private boolean isNotTooWorse(S solution) {
        return generator.nextDouble() < metropolisProbability(solution);
    }

    private double metropolisProbability(S solution) {
        double metropolisProbability = 0.0;
        if (currentSolution.isBetterThan(solution)) {
            double solutionQualityLoss = (currentSolution.value() - solution.value()) / currentSolution.value();
            metropolisProbability = Math.exp(-1 * solutionQualityLoss / temperature);
        }
        return metropolisProbability;
    }

    protected abstract double decreaseTemperature(double temperature);

    protected abstract double initialTemperature();

    protected abstract boolean meetStopCriteria();

    protected abstract void betterSolutionFound(S solution);

    protected abstract void updateCriteriaVariables();
}
