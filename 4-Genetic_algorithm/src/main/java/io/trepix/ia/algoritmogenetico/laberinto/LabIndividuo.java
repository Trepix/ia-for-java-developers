package io.trepix.ia.algoritmogenetico.laberinto;

import io.trepix.ia.algoritmogenetico.IGen;
import io.trepix.ia.algoritmogenetico.Individuo;
import io.trepix.ia.algoritmogenetico.Parametros;
import java.util.ArrayList;

// Un individuo se mueve en el laberinto
public class LabIndividuo extends Individuo {

    // Constructor por defecto : individuo aleatorio
    public LabIndividuo() {
        genoma = new ArrayList();
        for (int i = 0; i < Parametros.numGenes; i++) {
            genoma.add(new LabGen());
        }
    }
    
    // Constructor con un padre : copia y muta
    public LabIndividuo(LabIndividuo padre) {
        genoma = new ArrayList();
        for (IGen g : padre.genoma) {
            genoma.add(new LabGen((LabGen) g));
        }
        Mutar();
    }
    
    // Constructor con dos padres : crossover y muta
    public LabIndividuo(LabIndividuo padre1, LabIndividuo padre2) {
        genoma = new ArrayList();
        // Crossover
        int index = Parametros.random.nextInt(padre1.genoma.size());
        for (IGen g : padre1.genoma.subList(0, index)) {
            genoma.add(new LabGen((LabGen) g));
        }
        if (index < padre2.genoma.size()) {
            for (IGen g : padre2.genoma.subList(index, padre2.genoma.size())) {
                genoma.add(new LabGen((LabGen) g));
            }
        }
        // Mutación
        Mutar();
    }
    
    // Mutacion (eliminación, adición o modificación de genes)
    @Override
    public void Mutar() {
        // ¿Eliminación de un gen?
        if (Parametros.random.nextDouble() < Parametros.tasaEliminaGen) {
            int index = Parametros.random.nextInt(genoma.size());
            genoma.remove(index);
        }
        
        // ¿Adición de un gen al final?
        if (Parametros.random.nextDouble() < Parametros.tasaAgregaGen) {
            genoma.add(new LabGen());
        }
        
        // ¿Cambia valores?
        for(IGen g : genoma) {
            if (Parametros.random.nextDouble() < Parametros.tasaMutacion) {
                g.Mutar();
            }
        }
    }

    @Override
    public double Evaluar() {
        fitness = Laberinto.Evaluar(genoma);
        return fitness;
    }
}
