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
    public final void solve(Problem problem) {
        super.solve(problem);
        ConstruirSolucion();
        sendResult();
    }

    @Override
    public final Solution _solve(Problem problem) {
        super.solve(problem);
        return _ConstruirSolucion();
    }
    
    protected abstract void ConstruirSolucion();

    protected abstract Solution _ConstruirSolucion();
}
