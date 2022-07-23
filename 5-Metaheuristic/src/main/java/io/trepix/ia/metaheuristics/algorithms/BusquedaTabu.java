package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algoritmo;
import io.trepix.ia.metaheuristics.IHM;
import io.trepix.ia.metaheuristics.IProblema;
import io.trepix.ia.metaheuristics.ISolucion;
import java.util.ArrayList;

// Búsqueda tabu : nos movemos al mejor vocino no presente en la lista tabu
public abstract class BusquedaTabu extends Algoritmo {
    protected ISolucion solucionActual;
    protected ISolucion mejorSolucion;
    
    @Override
    public final void Resolver(IProblema pb, IHM ihm) {
        super.Resolver(pb, ihm);
        
        solucionActual = problema.SolucionAleatoria();
        mejorSolucion = solucionActual;
        AgregarListaTabu(solucionActual);
        
        while (!CriterioParada()) {
            ArrayList<ISolucion> vecindario = problema.Vecindario(solucionActual);
            if (vecindario != null) {
                vecindario = EliminarSolucionesTabues(vecindario);
                ISolucion mejorVecino = problema.MejorSolucion(vecindario);
                if (mejorVecino != null) {
                    Actualizar(mejorVecino);
                }
            }
            Incrementar();
        }
        EnviarResultado();
    }
    
    protected abstract void AgregarListaTabu(ISolucion solucion);
    protected abstract ArrayList<ISolucion> EliminarSolucionesTabues(ArrayList<ISolucion> vecindario);
    protected abstract boolean CriterioParada();
    protected abstract void Actualizar(ISolucion solucion);
    protected abstract void Incrementar();
}
