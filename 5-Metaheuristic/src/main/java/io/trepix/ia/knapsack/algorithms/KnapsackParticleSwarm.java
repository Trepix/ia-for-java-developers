package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
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
            KnapsackSolution solucion = (KnapsackSolution) sol;
            if (!solucion.equals(mejorSolucion)) {
                // Añadir un elemento de los mejores
                solucion = AgregarElemento(solucion, mejorSolucion);
                solucion = AgregarElemento(solucion, mejorActual);
                // Disminución del peso si es necesario
                int index;
                while (solucion.getPeso() > ((KnapsackProblem) problem).maximumWeight()) {
                    index = KnapsackProblem.generador.nextInt(solucion.contenido.size());
                    solucion.contenido.remove(index);
                }
                // Para terminar, se completa
                solucion = Completar(solucion);
            }
        }
    }
    
    protected KnapsackSolution AgregarElemento(KnapsackSolution solucion, Solution solucionSource) {
        int index = KnapsackProblem.generador.nextInt(((KnapsackSolution)solucionSource).contenido.size());
        Item b = ((KnapsackSolution)solucionSource).contenido.get(index);
        if (!solucion.contenido.contains(b)) {
            solucion.contenido.add(b);
        }
        return solucion;
    }
    
    protected KnapsackSolution Completar(KnapsackSolution solucion) {
        double espacioDispo = ((KnapsackProblem) problem).maximumWeight() - solucion.getPeso();
        ArrayList<Item> cajasPosibles = ((KnapsackProblem) problem).items();
        cajasPosibles.removeAll(solucion.contenido);
        ((KnapsackProblem) problem).EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        Item b;
        int index;
        while (!cajasPosibles.isEmpty()) {
            index = KnapsackProblem.generador.nextInt(cajasPosibles.size());
            b = cajasPosibles.get(index);
            solucion.contenido.add(b);
            cajasPosibles.remove(b);
            espacioDispo = ((KnapsackProblem) problem).maximumWeight() - solucion.getPeso();
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
