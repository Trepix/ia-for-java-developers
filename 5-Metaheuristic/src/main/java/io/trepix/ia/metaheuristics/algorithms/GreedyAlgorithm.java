package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
public abstract class GreedyAlgorithm<T extends Problem> extends Algorithm<T> {

    protected Problem problem;

    public GreedyAlgorithm() {
        super("Greedy Algorithm");
    }

    public final Solution solve(T problem) {
        return findSolution(problem);
    }

    protected abstract Solution findSolution(Problem problem);
}
