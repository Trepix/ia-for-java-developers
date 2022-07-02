package io.trepix.ia.aplicaciones;

import io.trepix.ia.algoritmogenetico.IHM;
import io.trepix.ia.algoritmogenetico.Individual;
import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.ProcesoEvolutivo;

public class Application implements IHM {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run() {
        // Resolución del viajante de comercio
        Configuration configuration  = new Configuration();
        configuration.tasaCrossover = 0.0;
        configuration.tasaMutacion = 0.3;
        configuration.tasaAgregaGen = 0.0;
        configuration.tasaEliminaGen = 0.0;
        configuration.minFitness = 2579;
        ProcesoEvolutivo process = new ProcesoEvolutivo(this, "PVC", configuration);
        process.run();
        
        // Resolución del laberinto
        Configuration configuration2 = new Configuration();
        configuration2.tasaCrossover = 0.6;
        configuration2.tasaMutacion = 0.1;
        configuration2.tasaAgregaGen = 0.8;
        configuration2.tasaEliminaGen = 0.1;
        configuration2.minFitness = 0;
        configuration2.numMaxGeneraciones = 300;
        ProcesoEvolutivo process2 = new ProcesoEvolutivo(this, "Lab", configuration2);
        process2.run();
    }
    
    @Override
    public void showBestIndividual(Individual ind, int generation) {
        System.out.println(generation + " -> " + ind.toString());
    }
}
