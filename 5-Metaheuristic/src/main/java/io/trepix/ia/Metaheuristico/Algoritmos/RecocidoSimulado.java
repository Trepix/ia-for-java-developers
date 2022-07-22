package io.trepix.ia.Metaheuristico.Algoritmos;

import io.trepix.ia.Metaheuristico.Algoritmo;
import io.trepix.ia.Metaheuristico.IHM;
import io.trepix.ia.Metaheuristico.IProblema;
import io.trepix.ia.Metaheuristico.ISolucion;
import java.util.ArrayList;

// Algoritmo del recorrido : se hacen aproximaciones cada vez más pequeñas con la temperatura que baja
public abstract class RecocidoSimulado extends Algoritmo {
    protected ISolucion solucionActual;
    protected ISolucion mejorSolucion;
    protected double temperatura;
    
    @Override
    public final void Resolver(IProblema pb, IHM ihm) {
        super.Resolver(pb, ihm);
        
        solucionActual = problema.SolucionAleatoria();
        mejorSolucion = solucionActual;
        InitializarTemperatura();
        
        while (!CriterioParada()) {
            ArrayList<ISolucion> vecindario = problema.Vecindario(solucionActual);
            if (vecindario != null) {
                ISolucion mejorVecino = problema.MejorSolucion(vecindario);
                Actualizar(mejorVecino);
            }
            Incrementar();
            ActualizarTemperatura();
        }
        EnviarResultado();
    }
    
    protected abstract void ActualizarTemperatura();
    protected abstract void InitializarTemperatura();
    protected abstract boolean CriterioParada();
    protected abstract void Actualizar(ISolucion solucion);
    protected abstract void Incrementar();
}
