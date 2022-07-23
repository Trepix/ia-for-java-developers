package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.IHM;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import java.util.ArrayList;

// Algoritmo por enjambre : varias soluciones se van a desplazar en el espacio de búsqueda
public abstract class EnjambreParticulas extends Algorithm {
    protected ArrayList<Solution> soluciones;
    protected Solution mejorSolucion;
    protected Solution mejorActual;

    protected final static int NUM_INDIVIDUOS = 30;
    
    @Override
    public final void Resolver(Problem problem, IHM ihm) {
        // Iinicialización
        super.Resolver(problem, ihm);
        soluciones = new ArrayList();
        for (int i = 0; i < NUM_INDIVIDUOS; i++) {
            Solution nuevaSol = this.problem.SolucionAleatoria();
            soluciones.add(nuevaSol);
        }
        mejorSolucion = this.problem.MejorSolucion(soluciones);
        mejorActual = mejorSolucion;
        
        // Bucle
        while (!CriterioParada()) {
            ActualizarVariables();
            ActualizarSoluciones();
            Incrementar();
        }
        
        sendResult();
    }
    
    protected abstract void ActualizarVariables();
    protected abstract void ActualizarSoluciones();
    protected abstract boolean CriterioParada();
    protected abstract void Incrementar();
}
