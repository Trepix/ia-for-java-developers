package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.Solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class ParticleSwarm<P extends Problem<S>, S extends Solution<S>> extends Algorithm<P, S> {

    private final static int PARTICLES = 30;
    private S bestSolution;
    private S bestCurrentSolution;

    protected P problem;

    public ParticleSwarm() {
        super("Particle Swarm");
    }

    @Override
    public final S solve(P problem) {
        this.problem = problem;
        Solutions<S> solutions = new Solutions<>(randomSolutions());

        bestSolution = solutions.best().orElseThrow();
        bestCurrentSolution = bestSolution;
        
        while (!meetStopCriteria()) {
            bestCurrentSolution = solutions.best().orElseThrow();
            if (bestCurrentSolution.isBetterThan(bestSolution)) {
                bestSolution = bestCurrentSolution;
            }
            solutions = improveSolutions(solutions);
            updateCriteriaVariables();
        }
        return bestSolution;
    }

    private Solutions<S> improveSolutions(Solutions<S> solutions) {
        ArrayList<S> newSolutions = new ArrayList<>();
        for (S solution : solutions) {
            if (!solution.equals(bestSolution)) {
                solution.improveWith(bestSolution);
                solution.improveWith(bestCurrentSolution);
                solution = complete(solution);
            }
            newSolutions.add(solution);
        }
        return new Solutions<>(newSolutions);
    }

    private List<S> randomSolutions() {
        return IntStream.range(0, PARTICLES)
                .mapToObj(__ -> problem.randomSolution())
                .collect(toList());
    }

    protected abstract S complete(S solution);

    protected abstract boolean meetStopCriteria();

    protected abstract void updateCriteriaVariables();
}
