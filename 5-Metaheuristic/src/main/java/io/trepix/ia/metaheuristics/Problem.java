package io.trepix.ia.metaheuristics;

public interface Problem<S extends Solution<S>> {

    Solutions<S> neighbours(S solution);

    S randomSolution();

}
