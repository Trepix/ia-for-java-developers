package aplicaciones;

import algoritmogenetico.IHM;
import algoritmogenetico.Individuo;
import algoritmogenetico.Argumentos;
import algoritmogenetico.ProcesoEvolutivo;

// Clase lanza las diferentes aplicaciones
public class Aplicacion implements IHM {
    public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.Run();
    }

    public void Run() {
        // Resolución del viajante de comercio
        // Argumentos
        Argumentos.tasaCrossover = 0.0;
        Argumentos.tasaMutacion = 0.3;
        Argumentos.tasaAgregaGen = 0.0;
        Argumentos.tasaEliminaGen = 0.0;
        Argumentos.minFitness = 2579;
        // Ejecución
        ProcesoEvolutivo sist = new ProcesoEvolutivo(this, "PVC");
        syst.Run();
        
        // Resolución del laberinto
        // Argumentos
        Argumentos.tasaCrossover = 0.6;
        Argumentos.tasaMutacion = 0.1;
        Argumentos.tasaAgregaGen = 0.8;
        Argumentos.tasaEliminaGen = 0.1;
        Argumentos.minFitness = 0;
        Argumentos.numMaxGeneraciones = 300;
        // Ejecución
        ProcesoEvolutivo sist2 = new ProcesoEvolutivo(this, "Lab");
        syst2.Run();
    }
    
    @Override
    public void MostrarMejorIndividuo(Individuo ind, int generation) {
        System.out.println(generation + " -> " + ind.toString());
    }
}
