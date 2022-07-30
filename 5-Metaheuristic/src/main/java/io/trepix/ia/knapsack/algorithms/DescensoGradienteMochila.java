package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.algorithms.DescensoGradiente;
import io.trepix.ia.metaheuristics.Solution;

// Descenso de gradiente para el problema de la mochila
public class DescensoGradienteMochila extends DescensoGradiente {
    protected int numIteracionesSinMejora = 0;
    protected final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 50;

    private final HumanMachineInterface hmi;

    public DescensoGradienteMochila(HumanMachineInterface hmi) {
        this.hmi = hmi;
    }

    @Override
    protected boolean CriterioParada() {
        return numIteracionesSinMejora >= NUM_MAX_ITERACIONES_SIN_MEJORA;
    }

    @Override
    protected void Incrementar() {
        numIteracionesSinMejora++;
    }
    
    @Override
    protected void Actualizar(Solution solucion) {
        if (solucion.value() > solucionActual.value()) {
            solucionActual = solucion;
            numIteracionesSinMejora = 0;
        }
    }

    @Override
    protected void sendResult() {
        hmi.show(solucionActual.toString());
    }

}
