package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.Solutions;

import java.util.ArrayList;

public abstract class ParticleSwarm<P extends Problem<S>, S extends Solution<S>> extends Algorithm<P, S> {

    protected final static int PARTICLES = 30;
    protected P problem;
    protected ArrayList<S> solutions;
    protected S bestSolution;
    protected S bestCurrentSolution;

    public ParticleSwarm() {
        super("Particle Swarm");
    }

    @Override
    public final S solve(P problem) {
        this.problem = problem;
        solutions = new ArrayList<>();
        for (int i = 0; i < PARTICLES; i++) {
            S solution = this.problem.randomSolution();
            solutions.add(solution);
        }

        bestSolution = new Solutions<>(solutions).best().get();
        bestCurrentSolution = bestSolution;
        
        while (!meetStopCriteria()) {
            bestCurrentSolution = new Solutions<>(solutions).best().get();
            if (bestCurrentSolution.isBetterThan(bestSolution)) {
                bestSolution = bestCurrentSolution;
            }
            updateSolutions();
            updateCriteriaVariables();
        }
        return bestSolution;
    }

    protected abstract void updateSolutions();

    protected abstract boolean meetStopCriteria();

    protected abstract void updateCriteriaVariables();
}
