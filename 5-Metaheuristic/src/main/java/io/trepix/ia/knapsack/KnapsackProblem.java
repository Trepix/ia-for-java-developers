package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.Solutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class KnapsackProblem implements Problem {
    public static Random generador = null;
    private final double maxWeight;
    private final int neighboursNumber;
    protected List<Item> cajasDispo;

    public KnapsackProblem(List<Item> items, Configuration configuration) {
        this.maxWeight = configuration.maximumKnapsackWeight();
        this.cajasDispo = items;
        generador = configuration.random();
        this.neighboursNumber = configuration.neighboursNumber();
    }

    public List<Item> items() {
        return new ArrayList<>(cajasDispo);
    }

    public Items _items() {
        return new Items(new ArrayList<>(cajasDispo));
    }

    @Override
    public Solution randomSolution() {
        var knapsack = this.emptyKnapsack();
        Items items = _items();
        items.removeWhichCannotBeCarried(knapsack);
        while (!knapsack.isFull() && !items.isEmpty()) {
            Item item = items.popRandom(generador);
            knapsack.add(item);
            items.removeWhichCannotBeCarried(knapsack);
        }
        return new KnapsackSolution(knapsack);
    }

    public void EliminarDemasiadoPesadas(List<Item> cajasPosibles, double espacioDispo) {
        Iterator<Item> iterador = cajasPosibles.iterator();
        while (iterador.hasNext()) {
            Item b = iterador.next();
            if (b.weight() > espacioDispo) {
                iterador.remove();
            }
        }
    }

    @Override
    public Solution MejorSolucion(List<Solution> soluciones) {
        if (!soluciones.isEmpty()) {
            Solution mejor = soluciones.get(0);
            for (Solution sol : soluciones) {
                if (sol.value() > mejor.value()) {
                    mejor = sol;
                }
            }
            return mejor;
        } else {
            return null;
        }
    }

    @Override
    public Solutions neighbours(Solution solution) {
        ArrayList<Solution> vecindario = new ArrayList();
        for (int i = 0; i < neighboursNumber; i++) {
            // Creación de un clon
            KnapsackSolution solucion = new KnapsackSolution((KnapsackSolution) solution);
            // se elimina un elemento
            int index = generador.nextInt(solucion.contenido.size());
            solucion.contenido.remove(index);
            // Cálculo de los pesos y de las cajas disponibles
            List<Item> cajasPosibles = items();
            double espacioDispo = maxWeight - solucion.getPeso();
            cajasPosibles.removeAll(solucion.contenido);
            EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
            // Se añaden las cajas
            while (espacioDispo > 0 && !cajasPosibles.isEmpty()) {
                // Elección aleatoria de una caja
                index = generador.nextInt(cajasPosibles.size());
                // Actualización de la solución
                Item b = cajasPosibles.get(index);
                solucion.contenido.add(b);
                cajasPosibles.remove(index);
                espacioDispo -= b.weight();
                // Actualización de la lista
                EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
            }
            vecindario.add(solucion);
        }
        return new Solutions(vecindario);
    }

    public Knapsack emptyKnapsack() {
        return new Knapsack(maximumWeight());
    }

    public double maximumWeight() {
        return maxWeight;
    }
}
