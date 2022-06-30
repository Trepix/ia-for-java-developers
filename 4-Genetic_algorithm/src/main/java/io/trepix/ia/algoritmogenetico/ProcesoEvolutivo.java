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
    
    // Constructor
    public ProcesoEvolutivo(IHM _ihm, String _problema) {
        ihm = _ihm;
        problema = _problema;
        FabricaIndividuos.getInstance().Init(problema);
        poblacion = new ArrayList();
        for (int i = 0; i < Parametros.numIndividuos; i++) {
            poblacion.add(FabricaIndividuos.getInstance().CrearIndividuo(problema));
        }
    }
    
    // Sobrevivir : sustitución por la nueva población
    private void Sobrevivir(ArrayList<Individual> nelleGeneracion) {
        poblacion = nelleGeneracion;
    }
    
    // Selección : torneo
    private Individual Seleccion() {
        int index1 = Parametros.random.nextInt(Parametros.numIndividuos);
        int index2 = Parametros.random.nextInt(Parametros.numIndividuos);
        if (poblacion.get(index1).fitness <= poblacion.get(index2).fitness) {
            return poblacion.get(index1);
        }
        else {
            return poblacion.get(index2);
        }
    }
    
    // Bucle principal
    public void run() {
        mejorFitness = Parametros.minFitness + 1;
        while(numGeneracion < Parametros.numMaxGeneraciones && mejorFitness > Parametros.minFitness) {
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
        for (int i = 0; i < Parametros.numIndividuos - 1; i++) {
            // Con o sin crossover ?
            if (Parametros.random.nextDouble() < Parametros.tasaCrossover) {
                // Avec crossover, donc dos padres
                Individual padre1 = Seleccion();
                Individual padre2 = Seleccion();
                // Reproducción
                nellePoblacion.add(FabricaIndividuos.getInstance().CrearIndividuo(problema, padre1, padre2));
            }
            else {
                // Sin crossover, un úncio padre
                Individual padre = Seleccion();
                // Reproducción
                nellePoblacion.add(FabricaIndividuos.getInstance().CrearIndividuo(problema, padre));
            }
        }
        return nellePoblacion;
    }
}
