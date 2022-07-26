package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

// Resolucion por algoritmo voraz : construcción progresiva de la solución
public abstract class AlgorithmVoraz extends Algorithm {

    public AlgorithmVoraz() {
        super("Greedy Algorithm");
    }

    @Override
    public final Solution solve(Problem problem) {
        super.solve(problem);
        return ConstruirSolucion();
    }

    protected abstract Solution ConstruirSolucion();
}
