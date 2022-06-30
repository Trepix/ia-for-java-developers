package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.IGen;
import io.trepix.ia.algoritmogenetico.Individuo;
import io.trepix.ia.algoritmogenetico.Parametros;
import java.util.ArrayList;

// Individuo del problema del viajante de comercio
public class PVCIndividuo extends Individuo {

    // Constructor por defecto : elección aleatoria del recorrido
    public PVCIndividuo() {
        genoma = new ArrayList();
        ArrayList<Integer> indexDispo = PVC.getCiudadesIndex();
        while(!indexDispo.isEmpty()) {
            int index = Parametros.random.nextInt(indexDispo.size());
            genoma.add(new PVCGen(indexDispo.get(index)));
            indexDispo.remove(index);
        }
    }
    
    // Mutacion : nos movemos un gen
    @Override
    public void Mutar() {
        if (Parametros.random.nextDouble() < Parametros.tasaMutacion) {
            int index1 = Parametros.random.nextInt(genoma.size());
            PVCGen g = (PVCGen)genoma.get(index1);
            genoma.remove(g);
            int index2 = Parametros.random.nextInt(genoma.size());
            genoma.add(index2, g);
        }
    }
    
    // Constructor con un padre
    public PVCIndividuo(PVCIndividuo padre) {
        genoma = new ArrayList();
        for (IGen g : padre.genoma) {
            this.genoma.add(new PVCGen((PVCGen)g));
        }
        Mutar();
    }

    // Constructor con dos padres
    public PVCIndividuo(PVCIndividuo padre1, PVCIndividuo padre2) {
        genoma = new ArrayList();
        // Crossover
        int ptCoupure = Parametros.random.nextInt(padre1.genoma.size());
        for(int i = 0; i < ptCoupure; i++) {
            genoma.add(new PVCGen((PVCGen) padre1.genoma.get(i)));
        }
        for (IGen g : padre2.genoma) {
            if (!genoma.contains((PVCGen)g)) {
                genoma.add(new PVCGen((PVCGen)g));
            }
        }
        // Mutacion
        Mutar();
    }
    
    // Evaluación de un individuo : cálculo de las distancias
    @Override
    public double Evaluar() {
        int kmTotal = 0;
        PVCGen antiguoGen = null;
        for (IGen g : genoma) {
            if (antiguoGen != null) {
                kmTotal += ((PVCGen)g).getDistancia(antiguoGen);
            }
            antiguoGen = (PVCGen)g;
        }
        kmTotal += antiguoGen.getDistancia((PVCGen)genoma.get(0));
        fitness = kmTotal;
        return fitness;
    }

}
