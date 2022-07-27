package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
public abstract class GreedyAlgorithm<T extends Problem> extends Algorithm<T> {

    protected Problem problem;

    public GreedyAlgorithm() {
        super("Greedy Algorithm");
    }

    @Override
    public final Solution solve(Problem problem) {
        return findSolution(problem);
    }

    public final Solution _solve(T problem) {
        return _findSolution(problem);
    }

    protected abstract Solution _findSolution(T problem);
    protected abstract Solution findSolution(Problem problem);
}
