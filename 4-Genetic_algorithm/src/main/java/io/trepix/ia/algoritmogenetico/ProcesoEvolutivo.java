package io.trepix.ia.algoritmogenetico;

import java.util.ArrayList;

// Sistema genérico que gestiona el proceso evolutivo
public class ProcesoEvolutivo {
    // Atributos
    protected ArrayList<Individuo> poblacion;
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
    private void Sobrevivir(ArrayList<Individuo> nelleGeneracion) {
        poblacion = nelleGeneracion;
    }
    
    // Selección : torneo
    private Individuo Seleccion() {
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
            Individuo mejorInd = EvaluarYRecuperarMejorInd(poblacion);
            mejorFitness = mejorInd.fitness;
            ArrayList<Individuo> nellePoblacion = Reproduccion(mejorInd);
            Sobrevivir(nellePoblacion);
            numGeneracion++;
        }
    }
    
    // Evalua toda la población y devuelve el mejor individuo
    private Individuo EvaluarYRecuperarMejorInd(ArrayList<Individuo> población) {
        Individuo mejorInd = poblacion.get(0);
        for(Individuo ind : población) {
            ind.Evaluar();
            if (ind.fitness < mejorInd.fitness) {
                mejorInd = ind;
            }
        }
        ihm.showBestIndividual(mejorInd, numGeneracion);
        return mejorInd;
    }
    
    // Selección y reproducción con elitismo, crossover y mutación
    private ArrayList<Individuo> Reproduccion(Individuo mejorInd) {
        ArrayList<Individuo> nellePoblacion = new ArrayList();
        nellePoblacion.add(mejorInd); // elitismo
        for (int i = 0; i < Parametros.numIndividuos - 1; i++) {
            // Con o sin crossover ?
            if (Parametros.random.nextDouble() < Parametros.tasaCrossover) {
                // Avec crossover, donc dos padres
                Individuo padre1 = Seleccion();
                Individuo padre2 = Seleccion();
                // Reproducción
                nellePoblacion.add(FabricaIndividuos.getInstance().CrearIndividuo(problema, padre1, padre2));
            }
            else {
                // Sin crossover, un úncio padre
                Individuo padre = Seleccion();
                // Reproducción
                nellePoblacion.add(FabricaIndividuos.getInstance().CrearIndividuo(problema, padre));
            }
        }
        return nellePoblacion;
    }
}
