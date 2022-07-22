package io.trepix.ia.Metaheuristico.Algoritmos;

import io.trepix.ia.Metaheuristico.Algoritmo;
import io.trepix.ia.Metaheuristico.IHM;
import io.trepix.ia.Metaheuristico.IProblema;
import io.trepix.ia.Metaheuristico.ISolucion;
import java.util.ArrayList;

// Algoritmo por enjambre : varias soluciones se van a desplazar en el espacio de búsqueda
public abstract class EnjambreParticulas extends Algoritmo {
    protected ArrayList<ISolucion> soluciones;
    protected ISolucion mejorSolucion;
    protected ISolucion mejorActual;

    protected final static int NUM_INDIVIDUOS = 30;
    
    @Override
    public final void Resolver(IProblema pb, IHM ihm) {
        // Iinicialización
        super.Resolver(pb, ihm);
        soluciones = new ArrayList();
        for (int i = 0; i < NUM_INDIVIDUOS; i++) {
            ISolucion nuevaSol = problema.SolucionAleatoria();
            soluciones.add(nuevaSol);
        }
        mejorSolucion = problema.MejorSolucion(soluciones);
        mejorActual = mejorSolucion;
        
        // Bucle
        while (!CriterioParada()) {
            ActualizarVariables();
            ActualizarSoluciones();
            Incrementar();
        }
        
        EnviarResultado();
    }
    
    protected abstract void ActualizarVariables();
    protected abstract void ActualizarSoluciones();
    protected abstract boolean CriterioParada();
    protected abstract void Incrementar();
}
