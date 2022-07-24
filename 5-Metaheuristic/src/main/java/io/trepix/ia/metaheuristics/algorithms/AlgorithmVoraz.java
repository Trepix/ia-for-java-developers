package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.Problem;

// Resolucion por algoritmo voraz : construcción progresiva de la solución
public abstract class AlgorithmVoraz extends Algorithm {

    public AlgorithmVoraz() {
        super("Algoritmo voraz");
    }

    @Override
    public final void Resolver(Problem problem, HumanMachineInterface ihm) {
        super.Resolver(problem, ihm);
        ConstruirSolucion();
        sendResult();
    }
    
    protected abstract void ConstruirSolucion();
}
