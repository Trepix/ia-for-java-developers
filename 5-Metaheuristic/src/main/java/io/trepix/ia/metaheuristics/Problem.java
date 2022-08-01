package io.trepix.ia.metaheuristics;

public interface Problem<S extends Solution> {

    Solutions<S> neighbours(S solution);

    S randomSolution();

}
