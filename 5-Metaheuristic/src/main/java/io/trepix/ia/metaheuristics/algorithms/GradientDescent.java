package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

public abstract class GradientDescent<P extends Problem<S>, S extends Solution> extends Algorithm<P, S> {
    protected S currentSolution;

    public GradientDescent() {
        super("Gradient Descent");
    }

    @Override
    public final S solve(P problem) {
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

    protected abstract void betterSolutionFound(S solution);

    protected abstract void updateCriteriaVariables();
}
