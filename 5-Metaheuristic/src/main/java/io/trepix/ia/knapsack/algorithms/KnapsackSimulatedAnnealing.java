package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.SimulatedAnnealing;

import java.util.Random;

// Recorrido simulado para el problema de la mochila
public class KnapsackSimulatedAnnealing extends SimulatedAnnealing<KnapsackProblem> {
    private final static int NUM_MAX_ITERACIONES = 100;
    private final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 30;
    private final Random generator;
    protected int numIteracionesSinMejora = 0;
    protected int numIteraciones = 0;

    public KnapsackSimulatedAnnealing(Random generator) {
        this.generator = generator;
    }

    @Override
    protected void ActualizarTemperatura() {
        temperatura *= 0.95;
    }

    @Override
    protected void InitializarTemperatura() {
        temperatura = 5;
    }

    @Override
    protected boolean CriterioParada() {
        return numIteraciones > NUM_MAX_ITERACIONES || numIteracionesSinMejora > NUM_MAX_ITERACIONES_SIN_MEJORA;
    }

    @Override
    protected void Actualizar(Solution solucion) {
        double probar = 0.0;
        if (solucion.value() < solucionActual.value()) {
            // Solución menos buena, se calcula la prueba de aceptación
            probar = Math.exp(-1 * (solucionActual.value() - solucion.value()) / solucionActual.value() / temperatura);
        }
        if (solucion.value() > solucionActual.value() || generator.nextDouble() < probar) {
            // Se acepta el cambio
            solucionActual = solucion;
            if (solucion.value() > mejorSolucion.value()) {
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

}
