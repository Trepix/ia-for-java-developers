package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

public abstract class GradientDescent<T extends Problem> extends Algorithm<T> {
    protected Solution currentSolution;

    public GradientDescent() {
        super("Gradient Descent");
    }

    @Override
    public final Solution solve(T problem) {
        currentSolution = problem.randomSolution();
        while (!meetStopCriteria()) {
            problem.neighbours(currentSolution)
                    .best()
                    .filter(solution -> solution.isBetterThan(currentSolution))
                    .ifPresent(solution -> {
                        currentSolution = solution;
                        betterSolutionFound(currentSolution);
                    });

            updateCriteriaVariables();
        }
        return currentSolution;
    }

    protected abstract boolean meetStopCriteria();

    protected abstract void betterSolutionFound(Solution solution);

    protected abstract void updateCriteriaVariables();
}
