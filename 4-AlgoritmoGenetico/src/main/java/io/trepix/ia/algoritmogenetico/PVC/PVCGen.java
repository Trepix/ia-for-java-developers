package algoritmogenetico.PVC;

import algoritmogenetico.IGen;

// Genes para el viajante de comercio
class PVCGen implements IGen {
    protected int ciudadIndice;
    
    // Constructores
    public PVCGen(int _ciudadIndice) {
        ciudadIndice = _ciudadIndice;
    }
    public PVCGen(PVCGen g) {
        ciudadIndice = g.ciudadIndice;
    }
    
    // Distancia entre este gen y otro
    protected int getDistancia(PVCGen g) {
        return PVC.getDistancia(ciudadIndice, g.ciudadIndice);
    }
    
    // Mutaci�n : imposible aqu�
    @Override
    public void Mutar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // Visualizaci�n
    public String toString() {
        return PVC.getCiudad(ciudadIndice);
    }
}
