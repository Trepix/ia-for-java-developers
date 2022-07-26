package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;

// Resolucion por algoritmo voraz : construcción progresiva de la solución
public abstract class AlgorithmVoraz extends Algorithm {

    public AlgorithmVoraz() {
        super("Greedy Algorithm");
    }

    @Override
    public final void solve(Problem problem) {
        super.solve(problem);
        ConstruirSolucion();
        sendResult();
    }
    
    protected abstract void ConstruirSolucion();
}
