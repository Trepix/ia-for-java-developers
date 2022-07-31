package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.ArrayList;

public abstract class ParticleSwarm<T extends Problem> extends Algorithm<T> {

    protected final static int PARTICLES = 30;
    protected Problem problem;
    protected ArrayList<Solution> solutions;
    protected Solution bestSolution;
    protected Solution bestCurrentSolution;

    public ParticleSwarm() {
        super("Particle Swarm");
    }

    @Override
    public final Solution solve(T problem) {
        this.problem = problem;
        solutions = new ArrayList<>();
        for (int i = 0; i < PARTICLES; i++) {
            Solution solution = this.problem.randomSolution();
            solutions.add(solution);
        }
        bestSolution = this.problem.MejorSolucion(solutions);
        bestCurrentSolution = bestSolution;
        
        while (!meetStopCriteria()) {
            updateVariables();
            updateSolutions();
            updateCriteriaVariables();
        }
        return bestSolution;
    }

    protected abstract void updateVariables();

    protected abstract void updateSolutions();

    protected abstract boolean meetStopCriteria();

    protected abstract void updateCriteriaVariables();
}
