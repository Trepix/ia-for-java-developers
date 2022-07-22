package io.trepix.ia.application.AlgoritmosMochila;

import io.trepix.ia.Metaheuristico.Algoritmos.DescensoGradiente;
import io.trepix.ia.Metaheuristico.ISolucion;

// Descenso de gradiente para el problema de la mochila
public class DescensoGradienteMochila extends DescensoGradiente {
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
    protected void Actualizar(ISolucion solucion) {
        if (solucion.getValor() > solucionActual.getValor()) {
            solucionActual = solucion;
            numIteracionesSinMejora = 0;
        }
    }

    @Override
    protected void EnviarResultado() {
        ihm.MostrarMensaje(solucionActual.toString());
    }

}
