package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public abstract class TabuSearch<T extends Problem> extends Algorithm<T> {

    private final Queue<Solution> tabuSolutions = new LinkedList<>();
    protected Solution currentSolution;
    protected Solution bestSolution;

    public TabuSearch() {
        super("Tabu Search");
    }

    @Override
    public final Solution solve(T problem) {
        currentSolution = problem.randomSolution();
        bestSolution = currentSolution;
        addTabuSolution(currentSolution);

        while (!meetStopCriteria()) {
            var neighbours = problem.neighbours(currentSolution);
            neighbours.removeAll(tabuSolutions);
            Solution bestSolution = problem.MejorSolucion(neighbours);
            Optional.ofNullable(bestSolution)
                    .ifPresent(this::update);
            updateCriteriaVariables();
        }
        return bestSolution;
    }


    private void addTabuSolution(Solution solution) {
        while (tabuSolutions.size() >= maxTabuSolutions()) {
            tabuSolutions.remove();
        }
        tabuSolutions.add(solution);
    }

    private void update(Solution solution) {
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
