package io.trepix.ia.algoritmogenetico;

import io.trepix.ia.algoritmogenetico.PVC.PVC;
import io.trepix.ia.algoritmogenetico.PVC.PVCIndividuo;
import io.trepix.ia.algoritmogenetico.laberinto.LabIndividuo;
import io.trepix.ia.algoritmogenetico.laberinto.Laberinto;

// Fábrica de individuos adaptados a los problemas (clase singleton)
class FabricaIndividuos {
    private static FabricaIndividuos instancia;
    
    private FabricaIndividuos() {}
    
    public static FabricaIndividuos getInstance() {
        if (instancia == null) {
            instancia = new FabricaIndividuos();
        }
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
    
    public Individuo CrearIndividuo(String type) {
        Individuo ind = null;
        switch (type) {
            case "PVC" :
                ind = new PVCIndividuo();
                break;
            case "Lab" :
                ind = new LabIndividuo();
                break;
        }
        return ind;
    }
    
    public Individuo CrearIndividuo(String type, Individuo padre) {
        Individuo ind = null;
        switch (type) {
            case "PVC" :
                ind = new PVCIndividuo((PVCIndividuo)padre);
                break;
            case "Lab" :
                ind = new LabIndividuo((LabIndividuo)padre);
                break;
        }
        return ind;
    }
    
    public Individuo CrearIndividuo(String type, Individuo padre1, Individuo padre2) {
        Individuo ind = null;
        switch (type) {
            case "PVC" :
                ind = new PVCIndividuo((PVCIndividuo)padre1, (PVCIndividuo)padre2);
                break;
            case "Lab" :
                ind = new LabIndividuo((LabIndividuo)padre1, (LabIndividuo)padre2);
                break;
        }
        return ind;
    }
}
