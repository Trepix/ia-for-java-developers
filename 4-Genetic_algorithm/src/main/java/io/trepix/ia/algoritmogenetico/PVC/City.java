package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.Gene;

// Genes para el viajante de comercio
class City implements Gene {
    protected int ciudadIndice;
    
    // Constructores
    public City(int _ciudadIndice) {
        ciudadIndice = _ciudadIndice;
    }
    public City(City g) {
        ciudadIndice = g.ciudadIndice;
    }
    
    // Distancia entre este gen y otro
    protected int getDistancia(City g) {
        return TravellingSalesmanProblem.getDistancia(ciudadIndice, g.ciudadIndice);
    }
    
    // Mutaci�n : imposible aqu�
    @Override
    public void mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // Visualizaci�n
    public String toString() {
        return TravellingSalesmanProblem.getCiudad(ciudadIndice);
    }
}
