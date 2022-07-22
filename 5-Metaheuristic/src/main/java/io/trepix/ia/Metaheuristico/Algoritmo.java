package io.trepix.ia.Metaheuristico;

// Algoritmo genérico
public abstract class Algoritmo {
    protected IProblema problema;
    protected IHM ihm;
    
    public void Resolver(IProblema _pb, IHM _ihm) {
        problema = _pb;
        ihm = _ihm;
    }
    
    protected abstract void EnviarResultado();
}
