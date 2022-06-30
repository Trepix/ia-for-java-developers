package io.trepix.ia.aplicaciones;

import io.trepix.ia.algoritmogenetico.IHM;
import io.trepix.ia.algoritmogenetico.Individuo;
import io.trepix.ia.algoritmogenetico.Parametros;
import io.trepix.ia.algoritmogenetico.ProcesoEvolutivo;

// Clase lanza las diferentes aplicaciones
public class Aplicacion implements IHM {
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.Run();
    }

    public void Run() {
        // Resolución del viajante de comercio
        // Argumentos
        Parametros.tasaCrossover = 0.0;
        Parametros.tasaMutacion = 0.3;
        Parametros.tasaAgregaGen = 0.0;
        Parametros.tasaEliminaGen = 0.0;
        Parametros.minFitness = 2579;
        // Ejecución
        ProcesoEvolutivo syst = new ProcesoEvolutivo(this, "PVC");
        syst.Run();
        
        // Resolución del laberinto
        // Argumentos
        Parametros.tasaCrossover = 0.6;
        Parametros.tasaMutacion = 0.1;
        Parametros.tasaAgregaGen = 0.8;
        Parametros.tasaEliminaGen = 0.1;
        Parametros.minFitness = 0;
        Parametros.numMaxGeneraciones = 300;
        // Ejecución
        ProcesoEvolutivo syst2 = new ProcesoEvolutivo(this, "Lab");
        syst2.Run();
    }
    
    @Override
    public void MostrarMejorIndividuo(Individuo ind, int generation) {
        System.out.println(generation + " -> " + ind.toString());
    }
}
