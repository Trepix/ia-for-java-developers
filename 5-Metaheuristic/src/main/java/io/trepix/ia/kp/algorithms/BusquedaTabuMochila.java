package io.trepix.ia.kp.algorithms;

import io.trepix.ia.metaheuristics.algorithms.BusquedaTabu;
import io.trepix.ia.metaheuristics.ISolucion;
import io.trepix.ia.kp.SolucionMochila;
import java.util.ArrayList;

// Búsqueda tabu para el problema de la mochila
public class BusquedaTabuMochila extends BusquedaTabu {
    protected int numIteracionesSinMejora = 0;
    protected int numIteraciones = 0;
    protected ArrayList<SolucionMochila> listaTabu = new ArrayList();
    
    private final static int NUM_MAX_ITERACIONES = 100;
    private final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 30;
    private final static int NUM_MAX_POSICIONES_TABU = 50;
    
    @Override
    protected boolean CriterioParada() {
        return (numIteraciones > NUM_MAX_ITERACIONES || numIteracionesSinMejora > NUM_MAX_ITERACIONES_SIN_MEJORA);
    }
    
    @Override
    protected void Actualizar(ISolucion solucion) {
        if (!listaTabu.contains(solucion)) {
            solucionActual = solucion;
            AgregarListaTabu(solucion);
            if (mejorSolucion.getValor() < solucion.getValor()) {
                mejorSolucion = solucion;
                numIteracionesSinMejora = 0;
            }
        }
    }
    
    @Override
    protected void Incrementar() {
        numIteracionesSinMejora++;
        numIteraciones++;
    }
    
    @Override
    protected void EnviarResultado() {
        ihm.MostrarMensaje(mejorSolucion.toString());
    }
    
    @Override
    protected void AgregarListaTabu(ISolucion solucion) {
        while (listaTabu.size() >= NUM_MAX_POSICIONES_TABU) {
            listaTabu.remove(0);
        }
        listaTabu.add((SolucionMochila) solucion);
    }

    @Override
    protected ArrayList<ISolucion> EliminarSolucionesTabues(ArrayList<ISolucion> vecindario) {
        vecindario.removeAll(listaTabu);
        return vecindario;
    }
}
