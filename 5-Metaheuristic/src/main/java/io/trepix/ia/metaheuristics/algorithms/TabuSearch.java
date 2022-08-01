package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.LinkedList;
import java.util.Queue;

public abstract class TabuSearch<P extends Problem<S>, S extends Solution> extends Algorithm<P, S> {

    private final Queue<S> tabuSolutions = new LinkedList<>();
    protected S currentSolution;
    protected S bestSolution;

    public TabuSearch() {
        super("Tabu Search");
    }

    @Override
    public final S solve(P problem) {
        currentSolution = problem.randomSolution();
        bestSolution = currentSolution;
        addTabuSolution(currentSolution);

        while (!meetStopCriteria()) {
            var solutions = problem.neighbours(currentSolution);
            solutions.remove(tabuSolutions);
            solutions.best().ifPresent(this::update);
            updateCriteriaVariables();
        }
        return bestSolution;
    }


    private void addTabuSolution(S solution) {
        while (tabuSolutions.size() >= maxTabuSolutions()) {
            tabuSolutions.remove();
        }
        tabuSolutions.add(solution);
    }

    private void update(S solution) {
        if (!tabuSolutions.contains(solution)) {
            currentSolution = solution;
            addTabuSolution(solution);
            if (solution.isBetterThan(bestSolution)) {
                bestSolution = solution;
                betterSolutionFound(bestSolution);
            }
        }
    }

    protected abstract boolean meetStopCriteria();

    protected abstract int maxTabuSolutions();

    protected abstract void betterSolutionFound(Solution solution);

    protected abstract void updateCriteriaVariables();
}
