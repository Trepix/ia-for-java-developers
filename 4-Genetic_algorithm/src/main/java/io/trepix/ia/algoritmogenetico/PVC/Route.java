package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.swap;

public class Route extends Individual {
    private final Configuration configuration;
    public Route(Configuration configuration, List<City> cities) {
        this.configuration = configuration;
        genome = new ArrayList<>(cities);
        Collections.shuffle(genome, configuration.random());
    }

    // Constructor con un padre
    public Route(Route padre) {
        this.configuration = padre.configuration;
        genome = new ArrayList<>();
        this.genome.addAll(padre.genome);
        mutate();
    }

    // Constructor con dos padres
    public Route(Route padre1, Route padre2) {
        this.configuration = padre1.configuration;
        genome = new ArrayList<>();
        // Crossover
        int ptCoupure = configuration.random().nextInt(padre1.genome.size());
        for (int i = 0; i < ptCoupure; i++) {
            genome.add(padre1.genome.get(i));
        }
        for (Gene g : padre2.genome) {
            if (!genome.contains(g)) {
                genome.add(g);
            }
        }
        // Mutacion
        mutate();
    }

    @Override
    public void mutate() {
        if (configuration.haveToMutate()) {
            int index1 = configuration.random().nextInt(genome.size());
            int index2 = configuration.random().nextInt(genome.size());
            swap(genome, index1, index2);
        }
    }

    // Evaluación de un individuo : cálculo de las distancias
    @Override
    protected double evaluate() {
        double kmTotal = 0;
        City antiguoGen = null;
        for (Gene gene : genome) {
            if (antiguoGen != null) {
                kmTotal += ((City) gene).distanceTo(antiguoGen);
            }
            antiguoGen = (City) gene;
        }
        kmTotal += antiguoGen.distanceTo((City) genome.get(0));
        return kmTotal;
    }

}
