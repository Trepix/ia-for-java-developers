package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
public abstract class GreedyAlgorithm extends Algorithm {

    protected Problem problem;

    public GreedyAlgorithm() {
        super("Greedy Algorithm");
    }

    @Override
    public final Solution solve(Problem problem) {
        return findSolution(problem);
    }

    protected abstract Solution findSolution(Problem problem);
}
