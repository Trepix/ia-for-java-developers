package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.Parametros;
import java.util.ArrayList;

// Individuo del problema del viajante de comercio
public class PVCIndividual extends Individual {

    // Constructor por defecto : elección aleatoria del recorrido
    public PVCIndividual() {
        genome = new ArrayList();
        ArrayList<Integer> indexDispo = PVC.getCiudadesIndex();
        while(!indexDispo.isEmpty()) {
            int index = Parametros.random.nextInt(indexDispo.size());
            genome.add(new PVCGen(indexDispo.get(index)));
            indexDispo.remove(index);
        }
    }
    
    // Mutacion : nos movemos un gen
    @Override
    public void mutate() {
        if (Parametros.random.nextDouble() < Parametros.tasaMutacion) {
            int index1 = Parametros.random.nextInt(genome.size());
            PVCGen g = (PVCGen) genome.get(index1);
            genome.remove(g);
            int index2 = Parametros.random.nextInt(genome.size());
            genome.add(index2, g);
        }
    }
    
    // Constructor con un padre
    public PVCIndividual(PVCIndividual padre) {
        genome = new ArrayList();
        for (Gene g : padre.genome) {
            this.genome.add(new PVCGen((PVCGen)g));
        }
        mutate();
    }

    // Constructor con dos padres
    public PVCIndividual(PVCIndividual padre1, PVCIndividual padre2) {
        genome = new ArrayList();
        // Crossover
        int ptCoupure = Parametros.random.nextInt(padre1.genome.size());
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
