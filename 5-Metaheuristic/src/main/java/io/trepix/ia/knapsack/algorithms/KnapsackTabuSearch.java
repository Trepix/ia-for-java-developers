package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.TabuSearch;

import java.util.ArrayList;
import java.util.List;

// Búsqueda tabu para el problema de la mochila
public class KnapsackTabuSearch extends TabuSearch<KnapsackProblem> {
    protected int iterationsWithoutImprovement = 0;
    protected int iterations = 0;
    protected ArrayList<KnapsackSolution> tabuSolutions = new ArrayList();
    
    private final static int NUM_MAX_ITERACIONES = 100;
    private final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 30;
    private final static int NUM_MAX_POSICIONES_TABU = 50;

    @Override
    protected boolean meetStopCriteria() {
        return (iterations > NUM_MAX_ITERACIONES || iterationsWithoutImprovement > NUM_MAX_ITERACIONES_SIN_MEJORA);
    }
    
    @Override
    protected void update(Solution solution) {
        if (!tabuSolutions.contains(solution)) {
            currentSolution = solution;
            addToTabuList(solution);
            if (bestSolution.value() < solution.value()) {
                bestSolution = solution;
                iterationsWithoutImprovement = 0;
            }
        }
    }
    
    @Override
    protected void updateCriteriaVariables() {
        iterationsWithoutImprovement++;
        iterations++;
    }

    @Override
    protected void addToTabuList(Solution solution) {
        while (tabuSolutions.size() >= NUM_MAX_POSICIONES_TABU) {
            tabuSolutions.remove(0);
        }
        tabuSolutions.add((KnapsackSolution) solution);
    }

    @Override
    protected List<Solution> deleteTabuSolutions(List<Solution> neighbours) {
        neighbours.removeAll(tabuSolutions);
        return neighbours;
    }
}
