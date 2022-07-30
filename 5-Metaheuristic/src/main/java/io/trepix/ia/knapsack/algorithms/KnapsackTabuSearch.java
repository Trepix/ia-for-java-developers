package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.algorithms.BusquedaTabu;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.knapsack.SolucionMochila;
import java.util.ArrayList;
import java.util.List;

// Búsqueda tabu para el problema de la mochila
public class KnapsackTabuSearch extends BusquedaTabu {
    protected int numIteracionesSinMejora = 0;
    protected int numIteraciones = 0;
    protected ArrayList<SolucionMochila> listaTabu = new ArrayList();
    
    private final static int NUM_MAX_ITERACIONES = 100;
    private final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 30;
    private final static int NUM_MAX_POSICIONES_TABU = 50;

    private final HumanMachineInterface hmi;

    public KnapsackTabuSearch(HumanMachineInterface hmi) {
        this.hmi = hmi;
    }

    @Override
    protected boolean CriterioParada() {
        return (numIteraciones > NUM_MAX_ITERACIONES || numIteracionesSinMejora > NUM_MAX_ITERACIONES_SIN_MEJORA);
    }
    
    @Override
    protected void Actualizar(Solution solucion) {
        if (!listaTabu.contains(solucion)) {
            solucionActual = solucion;
            AgregarListaTabu(solucion);
            if (mejorSolucion.value() < solucion.value()) {
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
    protected void sendResult() {
        hmi.show(mejorSolucion.toString());
    }
    
    @Override
    protected void AgregarListaTabu(Solution solucion) {
        while (listaTabu.size() >= NUM_MAX_POSICIONES_TABU) {
            listaTabu.remove(0);
        }
        listaTabu.add((SolucionMochila) solucion);
    }

    @Override
    protected List<Solution> EliminarSolucionesTabues(List<Solution> vecindario) {
        vecindario.removeAll(listaTabu);
        return vecindario;
    }
}
