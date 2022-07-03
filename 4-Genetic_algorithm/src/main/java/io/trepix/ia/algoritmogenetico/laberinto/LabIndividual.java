package io.trepix.ia.algoritmogenetico.laberinto;

import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.Configuration;
import java.util.ArrayList;

// Un individuo se mueve en el laberinto
public class LabIndividual extends Individual {

    private final Configuration configuration;

    // Constructor por defecto : individuo aleatorio
    public LabIndividual(Configuration configuration) {
        this.configuration = configuration;
        genome = new ArrayList();
        for (int i = 0; i < configuration.genesNumber(); i++) {
            genome.add(new LabGen(configuration));
        }
    }
    
    // Constructor con un padre : copia y muta
    public LabIndividual(LabIndividual padre) {
        configuration = padre.configuration;
        genome = new ArrayList();
        for (Gene g : padre.genome) {
            genome.add(new LabGen((LabGen) g));
        }
        mutate();
    }
    
    // Constructor con dos padres : crossover y muta
    public LabIndividual(LabIndividual padre1, LabIndividual padre2) {
        this.configuration = padre1.configuration;
        genome = new ArrayList();
        // Crossover
        int index = configuration.random().nextInt(padre1.genome.size());
        for (Gene g : padre1.genome.subList(0, index)) {
            genome.add(new LabGen((LabGen) g));
        }
        if (index < padre2.genome.size()) {
            for (Gene g : padre2.genome.subList(index, padre2.genome.size())) {
                genome.add(new LabGen((LabGen) g));
            }
        }
        // Mutación
        mutate();
    }
    
    // Mutacion (eliminación, adición o modificación de genes)
    @Override
    public void mutate() {
        // ¿Eliminación de un gen?
        if (configuration.random().nextDouble() < configuration.geneDeletionRate()) {
            int index = configuration.random().nextInt(genome.size());
            genome.remove(index);
        }
        
        // ¿Adición de un gen al final?
        if (configuration.random().nextDouble() < configuration.geneAggregationRate()) {
            genome.add(new LabGen(configuration));
        }
        
        // ¿Cambia valores?
        for(Gene g : genome) {
            if (configuration.random().nextDouble() < configuration.mutationRate()) {
                g.mutate();
            }
        }
    }

    @Override
    public double evaluate() {
        fitness = Laberinto.Evaluar(genome);
        return fitness;
    }
}
