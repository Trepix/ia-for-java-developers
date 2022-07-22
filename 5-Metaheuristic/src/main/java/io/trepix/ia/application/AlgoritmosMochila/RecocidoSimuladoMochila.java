package io.trepix.ia.application.AlgoritmosMochila;

import io.trepix.ia.Metaheuristico.Algoritmos.RecocidoSimulado;
import io.trepix.ia.Metaheuristico.ISolucion;
import io.trepix.ia.application.ProblemaMochila;

// Recorrido simulado para el problema de la mochila
public class RecocidoSimuladoMochila extends RecocidoSimulado {
    protected int numIteracionesSinMejora = 0;
    protected int numIteraciones = 0;
    
    private final static int NUM_MAX_ITERACIONES = 100;
    private final static int NUM_MAX_ITERACIONES_SIN_MEJORA = 30;
    
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
    protected void Actualizar(ISolucion solucion) {
        double probar = 0.0;
        if (solucion.getValor() < solucionActual.getValor()) {
            // Solución menos buena, se calcula la prueba de aceptación
            probar = Math.exp(-1 * (solucionActual.getValor() - solucion.getValor()) / solucionActual.getValor() / temperatura);
        }
        if (solucion.getValor() > solucionActual.getValor() || ProblemaMochila.generador.nextDouble() < probar) {
            // Se acepta el cambio
            solucionActual = solucion;
            if (solucion.getValor() > mejorSolucion.getValor()) {
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
}
