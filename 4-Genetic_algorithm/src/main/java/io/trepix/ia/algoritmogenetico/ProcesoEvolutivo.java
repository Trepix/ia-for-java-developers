package io.trepix.ia.algoritmogenetico;

import java.util.ArrayList;

// Sistema genérico que gestiona el proceso evolutivo
public class ProcesoEvolutivo {
    // Atributos
    protected ArrayList<Individual> poblacion;
    protected int numGeneracion = 0;
    protected IHM ihm = null;
    protected double mejorFitness;
    protected String problema;

    private final Configuration configuration;
    
    // Constructor
    public ProcesoEvolutivo(IHM _ihm, String _problema, Configuration configuration) {
        ihm = _ihm;
        problema = _problema;
        this.configuration = configuration;
        FabricaIndividuos.getInstance(this.configuration).Init(problema);
        poblacion = new ArrayList();
        for (int i = 0; i < configuration.numIndividuos; i++) {
            poblacion.add(FabricaIndividuos.getInstance(this.configuration).CrearIndividuo(problema));
        }
    }
    
    // Sobrevivir : sustitución por la nueva población
    private void Sobrevivir(ArrayList<Individual> nelleGeneracion) {
        poblacion = nelleGeneracion;
    }
    
    // Selección : torneo
    private Individual Seleccion() {
        int index1 = configuration.random.nextInt(configuration.numIndividuos);
        int index2 = configuration.random.nextInt(configuration.numIndividuos);
        if (poblacion.get(index1).fitness <= poblacion.get(index2).fitness) {
            return poblacion.get(index1);
        }
        else {
            return poblacion.get(index2);
        }
    }
    
    // Bucle principal
    public void run() {
        mejorFitness = configuration.minFitness + 1;
        while(numGeneracion < configuration.numMaxGeneraciones && mejorFitness > configuration.minFitness) {
            Individual mejorInd = EvaluarYRecuperarMejorInd(poblacion);
            mejorFitness = mejorInd.fitness;
            ArrayList<Individual> nellePoblacion = Reproduccion(mejorInd);
            Sobrevivir(nellePoblacion);
            numGeneracion++;
        }
    }
    
    // Evalua toda la población y devuelve el mejor individuo
    private Individual EvaluarYRecuperarMejorInd(ArrayList<Individual> population) {
        Individual mejorInd = poblacion.get(0);
        for(Individual ind : population) {
            ind.evaluate();
            if (ind.fitness < mejorInd.fitness) {
                mejorInd = ind;
            }
        }
        ihm.showBestIndividual(mejorInd, numGeneracion);
        return mejorInd;
    }
    
    // Selección y reproducción con elitismo, crossover y mutación
    private ArrayList<Individual> Reproduccion(Individual mejorInd) {
        ArrayList<Individual> nellePoblacion = new ArrayList();
        nellePoblacion.add(mejorInd); // elitismo
        for (int i = 0; i < configuration.numIndividuos - 1; i++) {
            // Con o sin crossover ?
            if (configuration.random.nextDouble() < configuration.tasaCrossover) {
                // Avec crossover, donc dos padres
                Individual padre1 = Seleccion();
                Individual padre2 = Seleccion();
                // Reproducción
                nellePoblacion.add(FabricaIndividuos.getInstance(this.configuration).CrearIndividuo(problema, padre1, padre2));
            }
            else {
                // Sin crossover, un úncio padre
                Individual padre = Seleccion();
                // Reproducción
                nellePoblacion.add(FabricaIndividuos.getInstance(this.configuration).CrearIndividuo(problema, padre));
            }
        }
        return nellePoblacion;
    }
}
