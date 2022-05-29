package Metaheuristico.Algoritmos;

import Metaheuristico.Algoritmo;
import Metaheuristico.IHM;
import Metaheuristico.IProblema;

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
