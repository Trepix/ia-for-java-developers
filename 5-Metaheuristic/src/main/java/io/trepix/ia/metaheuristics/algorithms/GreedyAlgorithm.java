package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

public abstract class GreedyAlgorithm<P extends Problem<S>, S extends Solution> extends Algorithm<P, S> {

    protected P problem;

    public GreedyAlgorithm() {
        super("Greedy Algorithm");
    }

    public final S solve(P problem) {
        return findSolution(problem);
    }

    protected abstract S findSolution(P problem);
}
