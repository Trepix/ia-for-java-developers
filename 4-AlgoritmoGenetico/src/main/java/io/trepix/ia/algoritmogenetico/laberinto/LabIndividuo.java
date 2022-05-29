package algoritmogenetico.laberinto;

import algoritmogenetico.IGen;
import algoritmogenetico.Individuo;
import algoritmogenetico.Argumentos;
import java.util.ArrayList;

// Un individuo se mueve en el laberinto
public class LabIndividuo extends Individuo {

    // Constructor por defecto : individuo aleatorio
    public LabIndividuo() {
        genoma = new ArrayList();
        for (int i = 0; i < Argumentos.numGenes; i++) {
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
        int index = Argumentos.random.nextInt(padre1.genoma.size());
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
        if (Argumentos.random.nextDouble() < Argumentos.tasaEliminaGen) {
            int index = Argumentos.random.nextInt(genoma.size());
            genoma.remove(index);
        }
        
        // ¿Adición de un gen al final?
        if (Argumentos.random.nextDouble() < Argumentos.tasaAgregaGen) {
            genoma.add(new LabGen());
        }
        
        // ¿Cambia valores?
        for(IGen g : genoma) {
            if (Argumentos.random.nextDouble() < Argumentos.tasaMutacion) {
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
