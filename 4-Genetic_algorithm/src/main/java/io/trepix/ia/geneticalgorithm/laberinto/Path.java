package io.trepix.ia.geneticalgorithm.laberinto;

import io.trepix.ia.geneticalgorithm.Gene;
import io.trepix.ia.geneticalgorithm.Individual;
import io.trepix.ia.geneticalgorithm.Configuration;
import java.util.ArrayList;

// Un individuo se mueve en el laberinto
public class Path extends Individual {

    private final Configuration configuration;

    // Constructor por defecto : individuo aleatorio
    public Path(Configuration configuration) {
        this.configuration = configuration;
        genome = new ArrayList();
        for (int i = 0; i < configuration.genesNumber(); i++) {
            genome.add(new DirectionUntilNextIntersection(configuration));
        }
    }
    
    // Constructor con un padre : copia y muta
    public Path(Path padre) {
        configuration = padre.configuration;
        genome = new ArrayList();
        for (Gene g : padre.genome) {
            genome.add(new DirectionUntilNextIntersection((DirectionUntilNextIntersection) g));
        }
        mutate();
    }
    
    // Constructor con dos padres : crossover y muta
    public Path(Path padre1, Path padre2) {
        this.configuration = padre1.configuration;
        genome = new ArrayList();
        // Crossover
        int index = configuration.random().nextInt(padre1.genome.size());
        for (Gene g : padre1.genome.subList(0, index)) {
            genome.add(new DirectionUntilNextIntersection((DirectionUntilNextIntersection) g));
        }
        if (index < padre2.genome.size()) {
            for (Gene g : padre2.genome.subList(index, padre2.genome.size())) {
                genome.add(new DirectionUntilNextIntersection((DirectionUntilNextIntersection) g));
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
            genome.add(new DirectionUntilNextIntersection(configuration));
        }
        
        // ¿Cambia valores?
        for(Gene g : genome) {
            if (configuration.random().nextDouble() < configuration.mutationRate()) {
                g.mutate();
            }
        }
    }

    @Override
    protected double evaluate() {
        return Laberinto.Evaluar(genome);
    }
}
