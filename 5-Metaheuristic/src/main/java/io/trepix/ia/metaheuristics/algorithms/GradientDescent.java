package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

public abstract class GradientDescent<T extends Problem> extends Algorithm<T> {
    protected Solution currentSolution;

    public GradientDescent() {
        super("Gradient Descent");
    }

    @Override
    public final Solution solve(T problem) {
        currentSolution = problem.randomSolution();
        while (!meetStopCriteria()) {
            List<Solution> neighboursSolution = problem.neighbours(currentSolution);
            var solution = problem.MejorSolucion(neighboursSolution);
            if (solution.isBetterThan(currentSolution)) {
                currentSolution = solution;
                bestSolutionFound(currentSolution);
            }
            updateCriteriaVariables();
        }
        return currentSolution;
    }

    protected abstract boolean meetStopCriteria();

    protected abstract void bestSolutionFound(Solution solution);

    protected abstract void updateCriteriaVariables();
}
