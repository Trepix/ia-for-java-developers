package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.*;

// El problema de la mochila a resolver (maximizar el valor sin sobrepasar el peso)
public class KnapsackProblem implements Problem {
    protected List<Item> cajasDispo;
    private double pesosMax;
    public static Random generador = null;
    private final int NUM_VECINOS;

    public KnapsackProblem(List<Item> items, Configuration configuration) {
        this.pesosMax = configuration.maximumKnapsackWeight();
        this.cajasDispo = items;
        generador = configuration.random();
        this.NUM_VECINOS = configuration.neighboursNumber();
    }

    public ArrayList<Item> items() {
        ArrayList<Item> copia = new ArrayList();
        copia.addAll(cajasDispo);
        return copia;
    }
    
    @Override
    public Solution randomSolution() {
        KnapsackSolution solucion = new KnapsackSolution();
        ArrayList<Item> cajasPosibles = items();
        double espacioDispo = pesosMax;
        // Se eliminan las demasiado pesadas
        EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        while(espacioDispo > 0 && !cajasPosibles.isEmpty()) {
            // Elección aleatoria de una caja
            int index = generador.nextInt(cajasPosibles.size());
            // Actualización de la solución
            Item b = cajasPosibles.get(index);
            solucion.contenido.add(b);
            cajasPosibles.remove(index);
            espacioDispo -= b.weight();
            // Actualización de la lista
            EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        }
        return solucion;
    }
    
    public void EliminarDemasiadoPesadas(ArrayList<Item> cajasPosibles, double espacioDispo) {
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
        }
        else {
            return null;
        }
    }
    
    @Override
    public List<Solution> neighbours(Solution solucionActual) {
        ArrayList<Solution> vecindario = new ArrayList();
        for (int i = 0; i < NUM_VECINOS; i++) {
            // Creación de un clon
            KnapsackSolution solucion = new KnapsackSolution((KnapsackSolution) solucionActual);
            // se elimina un elemento
            int index = generador.nextInt(solucion.contenido.size());
            solucion.contenido.remove(index);
            // Cálculo de los pesos y de las cajas disponibles
            ArrayList<Item> cajasPosibles = items();
            double espacioDispo = pesosMax - solucion.getPeso();
            cajasPosibles.removeAll(solucion.contenido);
            EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
            // Se añaden las cajas
            while(espacioDispo > 0 && !cajasPosibles.isEmpty()) {
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
        return vecindario;
    }

    @Override
    public Optional<Solution> bestNeighbour(Solution solucionActual) {
        return this.neighbours(solucionActual).stream().max(Solution::compareTo);
    }

    public Knapsack emptyKnapsack() {
        return new Knapsack(maximumWeight());
    }

    public double maximumWeight() {
        return pesosMax;
    }
}
