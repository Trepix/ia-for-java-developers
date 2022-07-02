package io.trepix.ia.algoritmogenetico;

import io.trepix.ia.algoritmogenetico.PVC.PVC;
import io.trepix.ia.algoritmogenetico.PVC.PVCIndividual;
import io.trepix.ia.algoritmogenetico.laberinto.LabIndividual;
import io.trepix.ia.algoritmogenetico.laberinto.Laberinto;

// Fábrica de individuos adaptados a los problemas (clase singleton)
class FabricaIndividuos {
    private static FabricaIndividuos instancia;
    private final Configuration configuration;

    private FabricaIndividuos(Configuration configuration) {
        this.configuration = configuration;
    }
    
    public static FabricaIndividuos getInstance(Configuration configuration) {
        instancia = new FabricaIndividuos(configuration);
        return instancia;
    }
    
    void Init(String type) {
        switch (type) {
            case "PVC" :
                PVC.Init();
                break;
            case "Lab" :
                Laberinto.Init(Laberinto.Lab2);
                break;
        }
    }
    
    public Individual CrearIndividuo(String type) {
        Individual ind = null;
        switch (type) {
            case "PVC" :
                ind = new PVCIndividual(this.configuration);
                break;
            case "Lab" :
                ind = new LabIndividual(this.configuration);
                break;
        }
        return ind;
    }
    
    public Individual CrearIndividuo(String type, Individual padre) {
        Individual ind = null;
        switch (type) {
            case "PVC" :
                ind = new PVCIndividual((PVCIndividual)padre);
                break;
            case "Lab" :
                ind = new LabIndividual((LabIndividual)padre);
                break;
        }
        return ind;
    }
    
    public Individual CrearIndividuo(String type, Individual padre1, Individual padre2) {
        Individual ind = null;
        switch (type) {
            case "PVC" :
                ind = new PVCIndividual((PVCIndividual)padre1, (PVCIndividual)padre2);
                break;
            case "Lab" :
                ind = new LabIndividual((LabIndividual)padre1, (LabIndividual)padre2);
                break;
        }
        return ind;
    }
}
