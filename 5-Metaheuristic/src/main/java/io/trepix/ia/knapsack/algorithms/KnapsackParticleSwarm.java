package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Caja;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.SolucionMochila;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.EnjambreParticulas;

import java.util.ArrayList;

// Enjambre particular para el problema de la mochila
public class KnapsackParticleSwarm extends EnjambreParticulas<KnapsackProblem> {
    protected int numIteraciones = 0;
    private final static int NUM_MAX_ITERACIONES = 200;

    @Override
    protected void ActualizarSoluciones() {
        for (Solution sol : soluciones) {
            SolucionMochila solucion = (SolucionMochila) sol;
            if (!solucion.equals(mejorSolucion)) {
                // Añadir un elemento de los mejores
                solucion = AgregarElemento(solucion, mejorSolucion);
                solucion = AgregarElemento(solucion, mejorActual);
                // Disminución del peso si es necesario
                int index;
                while (solucion.getPeso() > ((KnapsackProblem) problem).pesosMax) {
                    index = KnapsackProblem.generador.nextInt(solucion.contenido.size());
                    solucion.contenido.remove(index);
                }
                // Para terminar, se completa
                solucion = Completar(solucion);
            }
        }
    }
    
    protected SolucionMochila AgregarElemento(SolucionMochila solucion, Solution solucionSource) {
        int index = KnapsackProblem.generador.nextInt(((SolucionMochila)solucionSource).contenido.size());
        Caja b = ((SolucionMochila)solucionSource).contenido.get(index);
        if (!solucion.contenido.contains(b)) {
            solucion.contenido.add(b);
        }
        return solucion;
    }
    
    protected SolucionMochila Completar(SolucionMochila solucion) {
        double espacioDispo = ((KnapsackProblem) problem).pesosMax - solucion.getPeso();
        ArrayList<Caja> cajasPosibles = ((KnapsackProblem) problem).Cajas();
        cajasPosibles.removeAll(solucion.contenido);
        ((KnapsackProblem) problem).EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        Caja b;
        int index;
        while (!cajasPosibles.isEmpty()) {
            index = KnapsackProblem.generador.nextInt(cajasPosibles.size());
            b = cajasPosibles.get(index);
            solucion.contenido.add(b);
            cajasPosibles.remove(b);
            espacioDispo = ((KnapsackProblem) problem).pesosMax - solucion.getPeso();
            ((KnapsackProblem) problem).EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        }
        return solucion;
    }
    
    @Override
    protected void ActualizarVariables() {
        mejorActual = soluciones.get(0);
        for (Solution sol : soluciones) {
            if (sol.value() > mejorActual.value()) {
                mejorActual = sol;
            }
        }
        if (mejorActual.value() > mejorSolucion.value()) {
            mejorSolucion = mejorActual;
        }
    }

    @Override
    protected boolean CriterioParada() {
        return numIteraciones > NUM_MAX_ITERACIONES;
    }

    @Override
    protected void Incrementar() {
        numIteraciones++;
    }

}
