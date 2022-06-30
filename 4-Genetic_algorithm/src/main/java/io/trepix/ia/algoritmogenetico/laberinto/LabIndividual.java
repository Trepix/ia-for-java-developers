package io.trepix.ia.algoritmogenetico.laberinto;

import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.Parametros;
import java.util.ArrayList;

// Un individuo se mueve en el laberinto
public class LabIndividual extends Individual {

    // Constructor por defecto : individuo aleatorio
    public LabIndividual() {
        genome = new ArrayList();
        for (int i = 0; i < Parametros.numGenes; i++) {
            genome.add(new LabGen());
        }
    }
    
    // Constructor con un padre : copia y muta
    public LabIndividual(LabIndividual padre) {
        genome = new ArrayList();
        for (Gene g : padre.genome) {
            genome.add(new LabGen((LabGen) g));
        }
        mutate();
    }
    
    // Constructor con dos padres : crossover y muta
    public LabIndividual(LabIndividual padre1, LabIndividual padre2) {
        genome = new ArrayList();
        // Crossover
        int index = Parametros.random.nextInt(padre1.genome.size());
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
        if (Parametros.random.nextDouble() < Parametros.tasaEliminaGen) {
            int index = Parametros.random.nextInt(genome.size());
            genome.remove(index);
        }
        
        // ¿Adición de un gen al final?
        if (Parametros.random.nextDouble() < Parametros.tasaAgregaGen) {
            genome.add(new LabGen());
        }
        
        // ¿Cambia valores?
        for(Gene g : genome) {
            if (Parametros.random.nextDouble() < Parametros.tasaMutacion) {
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
