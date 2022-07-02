package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.Configuration;
import java.util.ArrayList;

// Individuo del problema del viajante de comercio
public class PVCIndividual extends Individual {

    // Constructor por defecto : elección aleatoria del recorrido

    private final Configuration configuration;
    public PVCIndividual(Configuration configuration) {
        this.configuration = configuration;
        genome = new ArrayList();
        ArrayList<Integer> indexDispo = PVC.getCiudadesIndex();
        while(!indexDispo.isEmpty()) {
            int index = configuration.random.nextInt(indexDispo.size());
            genome.add(new PVCGen(indexDispo.get(index)));
            indexDispo.remove(index);
        }
    }
    
    // Mutacion : nos movemos un gen
    @Override
    public void mutate() {
        if (configuration.random.nextDouble() < configuration.tasaMutacion) {
            int index1 = configuration.random.nextInt(genome.size());
            PVCGen g = (PVCGen) genome.get(index1);
            genome.remove(g);
            int index2 = configuration.random.nextInt(genome.size());
            genome.add(index2, g);
        }
    }
    
    // Constructor con un padre
    public PVCIndividual(PVCIndividual padre) {
        this.configuration = padre.configuration;
        genome = new ArrayList();
        for (Gene g : padre.genome) {
            this.genome.add(new PVCGen((PVCGen)g));
        }
        mutate();
    }

    // Constructor con dos padres
    public PVCIndividual(PVCIndividual padre1, PVCIndividual padre2) {
        this.configuration = padre1.configuration;
        genome = new ArrayList();
        // Crossover
        int ptCoupure = configuration.random.nextInt(padre1.genome.size());
        for(int i = 0; i < ptCoupure; i++) {
            genome.add(new PVCGen((PVCGen) padre1.genome.get(i)));
        }
        for (Gene g : padre2.genome) {
            if (!genome.contains((PVCGen)g)) {
                genome.add(new PVCGen((PVCGen)g));
            }
        }
        // Mutacion
        mutate();
    }
    
    // Evaluación de un individuo : cálculo de las distancias
    @Override
    public double evaluate() {
        int kmTotal = 0;
        PVCGen antiguoGen = null;
        for (Gene g : genome) {
            if (antiguoGen != null) {
                kmTotal += ((PVCGen)g).getDistancia(antiguoGen);
            }
            antiguoGen = (PVCGen)g;
        }
        kmTotal += antiguoGen.getDistancia((PVCGen) genome.get(0));
        fitness = kmTotal;
        return fitness;
    }

}
