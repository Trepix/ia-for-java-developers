package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algoritmo;
import io.trepix.ia.metaheuristics.IHM;
import io.trepix.ia.metaheuristics.IProblema;

// Resolucion por algoritmo voraz : construcción progresiva de la solución
public abstract class AlgoritmoVoraz extends Algoritmo {
    @Override
    public final void Resolver(IProblema pb, IHM ihm) {
        super.Resolver(pb, ihm);
        ConstruirSolucion();
        EnviarResultado();
    }
    
    protected abstract void ConstruirSolucion();
}
