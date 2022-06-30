package io.trepix.ia.aplicaciones;

import io.trepix.ia.algoritmogenetico.IHM;
import io.trepix.ia.algoritmogenetico.Individuo;
import io.trepix.ia.algoritmogenetico.Parametros;
import io.trepix.ia.algoritmogenetico.ProcesoEvolutivo;

public class Application implements IHM {
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run() {
        // Resolución del viajante de comercio
        Parametros.tasaCrossover = 0.0;
        Parametros.tasaMutacion = 0.3;
        Parametros.tasaAgregaGen = 0.0;
        Parametros.tasaEliminaGen = 0.0;
        Parametros.minFitness = 2579;
        ProcesoEvolutivo process = new ProcesoEvolutivo(this, "PVC");
        process.run();
        
        // Resolución del laberinto
        Parametros.tasaCrossover = 0.6;
        Parametros.tasaMutacion = 0.1;
        Parametros.tasaAgregaGen = 0.8;
        Parametros.tasaEliminaGen = 0.1;
        Parametros.minFitness = 0;
        Parametros.numMaxGeneraciones = 300;
        ProcesoEvolutivo process2 = new ProcesoEvolutivo(this, "Lab");
        process2.run();
    }
    
    @Override
    public void showBestIndividual(Individuo ind, int generation) {
        System.out.println(generation + " -> " + ind.toString());
    }
}
