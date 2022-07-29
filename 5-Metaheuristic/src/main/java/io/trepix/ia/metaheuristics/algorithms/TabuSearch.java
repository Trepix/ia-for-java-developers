package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

public abstract class TabuSearch<T extends Problem> extends Algorithm<T> {
    protected Solution currentSolution;
    protected Solution bestSolution;

    public TabuSearch() {
        super("Tabu Search");
    }

    @Override
    public final Solution solve(T problem) {
        currentSolution = problem.randomSolution();
        bestSolution = currentSolution;
        addToTabuList(currentSolution);

        while (!meetStopCriteria()) {
            List<Solution> vecindario = problem.neighbours(currentSolution);
            if (vecindario != null) {
                vecindario = deleteTabuSolutions(vecindario);
                Solution bestSolution = problem.MejorSolucion(vecindario);
                if (bestSolution != null) {
                    update(bestSolution);
                }
            }
            updateCriteriaVariables();
        }
        return bestSolution;
    }

    protected abstract void addToTabuList(Solution solution);

    protected abstract List<Solution> deleteTabuSolutions(List<Solution> neighbours);

    protected abstract boolean meetStopCriteria();

    protected abstract void update(Solution solution);

    protected abstract void updateCriteriaVariables();
}
