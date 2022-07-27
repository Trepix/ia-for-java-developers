package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.DescensoGradiente;

// Descenso de gradiente para el problema de la mochila
public class KnapsackGradientDescent extends DescensoGradiente<KnapsackProblem> {
    protected int numIteracionesSinMejora = 0;
    protected final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 50;

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

}
