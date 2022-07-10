package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.Configuration;
import java.util.ArrayList;

// Individuo del problema del viajante de comercio
public class Route extends Individual {

    // Constructor por defecto : elección aleatoria del recorrido

    private final Configuration configuration;
    public Route(Configuration configuration) {
        this.configuration = configuration;
        genome = new ArrayList();
        ArrayList<Integer> indexDispo = TravellingSalesmanProblem.getCiudadesIndex();
        while(!indexDispo.isEmpty()) {
            int index = configuration.random().nextInt(indexDispo.size());
            genome.add(new City(indexDispo.get(index)));
            indexDispo.remove(index);
        }
    }
    
    // Mutacion : nos movemos un gen
    @Override
    public void mutate() {
        if (configuration.random().nextDouble() < configuration.mutationRate()) {
            int index1 = configuration.random().nextInt(genome.size());
            City g = (City) genome.get(index1);
            genome.remove(g);
            int index2 = configuration.random().nextInt(genome.size());
            genome.add(index2, g);
        }
    }
    
    // Constructor con un padre
    public Route(Route padre) {
        this.configuration = padre.configuration;
        genome = new ArrayList();
        for (Gene g : padre.genome) {
            this.genome.add(new City((City)g));
        }
        mutate();
    }

    // Constructor con dos padres
    public Route(Route padre1, Route padre2) {
        this.configuration = padre1.configuration;
        genome = new ArrayList();
        // Crossover
        int ptCoupure = configuration.random().nextInt(padre1.genome.size());
        for(int i = 0; i < ptCoupure; i++) {
            genome.add(new City((City) padre1.genome.get(i)));
        }
        for (Gene g : padre2.genome) {
            if (!genome.contains((City)g)) {
                genome.add(new City((City)g));
            }
        }
        // Mutacion
        mutate();
    }
    
    // Evaluación de un individuo : cálculo de las distancias
    @Override
    protected double evaluate() {
        double kmTotal = 0;
        City antiguoGen = null;
        for (Gene g : genome) {
            if (antiguoGen != null) {
                kmTotal += ((City)g).getDistancia(antiguoGen);
            }
            antiguoGen = (City)g;
        }
        kmTotal += antiguoGen.getDistancia((City) genome.get(0));
        return kmTotal;
    }

}
