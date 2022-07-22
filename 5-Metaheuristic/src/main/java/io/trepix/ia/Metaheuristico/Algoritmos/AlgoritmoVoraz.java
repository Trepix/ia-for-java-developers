package io.trepix.ia.Metaheuristico.Algoritmos;

import io.trepix.ia.Metaheuristico.Algoritmo;
import io.trepix.ia.Metaheuristico.IHM;
import io.trepix.ia.Metaheuristico.IProblema;

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
