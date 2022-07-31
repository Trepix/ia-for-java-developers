package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.ParticleSwarm;

import java.util.List;

// Enjambre particular para el problema de la mochila
public class KnapsackParticleSwarm extends ParticleSwarm<KnapsackProblem> {
    protected int numIteraciones = 0;
    private final static int NUM_MAX_ITERACIONES = 200;

    @Override
    protected void updateSolutions() {
        for (Solution sol : solutions) {
            KnapsackSolution solucion = (KnapsackSolution) sol;
            if (!solucion.equals(bestSolution)) {
                // Añadir un elemento de los mejores
                solucion = AgregarElemento(solucion, bestSolution);
                solucion = AgregarElemento(solucion, bestCurrentSolution);
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
        List<Item> cajasPosibles = ((KnapsackProblem) problem).items();
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
    protected void updateVariables() {
        bestCurrentSolution = solutions.get(0);
        for (Solution sol : solutions) {
            if (sol.value() > bestCurrentSolution.value()) {
                bestCurrentSolution = sol;
            }
        }
        if (bestCurrentSolution.value() > bestSolution.value()) {
            bestSolution = bestCurrentSolution;
        }
    }

    @Override
    protected boolean meetStopCriteria() {
        return numIteraciones > NUM_MAX_ITERACIONES;
    }

    @Override
    protected void updateCriteriaVariables() {
        numIteraciones++;
    }

}
