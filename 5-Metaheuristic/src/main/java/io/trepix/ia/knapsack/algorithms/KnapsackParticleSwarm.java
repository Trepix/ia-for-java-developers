package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Item;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackSolution;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.ParticleSwarm;

import java.util.ArrayList;
import java.util.Random;

public class KnapsackParticleSwarm extends ParticleSwarm<KnapsackProblem> {
    private final Random generator;
    protected int iterations = 0;
    private final static int MAX_ITERATIONS = 200;

    public KnapsackParticleSwarm(Random generator) {
        super();
        this.generator = generator;
    }

    @Override
    protected void updateSolutions() {
        ArrayList<Solution> newSolutions = new ArrayList<>();
        for (Solution sol : solutions) {
            KnapsackSolution solucion = (KnapsackSolution) sol;
            if (!solucion.equals(bestSolution)) {
                // Añadir un elemento de los mejores
                solucion = AgregarElemento(solucion, bestSolution);
                solucion = AgregarElemento(solucion, bestCurrentSolution);
                // Disminución del peso si es necesario
                int index;
                while (solucion.getPeso() > ((KnapsackProblem)problem).maximumWeight()) {
                    index = generator.nextInt(solucion.contenido.size());
                    solucion.contenido.remove(index);
                }
                // Para terminar, se completa
                solucion = fillKnapsack(solucion);
            }
            newSolutions.add(solucion);
        }
        solutions = newSolutions;
    }
    
    protected KnapsackSolution AgregarElemento(KnapsackSolution solucion, Solution solucionSource) {
        int index = generator.nextInt(((KnapsackSolution)solucionSource).contenido.size());
        Item b = ((KnapsackSolution)solucionSource).contenido.get(index);
        if (!solucion.contenido.contains(b)) {
            solucion.contenido.add(b);
        }
        return solucion;
    }
    
    protected KnapsackSolution fillKnapsack(KnapsackSolution solution) {
        var knapsack = solution.knapsack();
        var allItems = ((KnapsackProblem)problem)._items();
        allItems.removeUsedItems(knapsack);
        allItems.removeWhichCannotBeCarried(knapsack);

        while (allItems.isNotEmpty()) {
            Item item = allItems.popRandom(generator);
            knapsack.add(item);
            allItems.removeWhichCannotBeCarried(knapsack);
        }
        return new KnapsackSolution(knapsack);
    }


    @Override
    protected boolean meetStopCriteria() {
        return iterations > MAX_ITERATIONS;
    }

    @Override
    protected void updateCriteriaVariables() {
        iterations++;
    }

}
