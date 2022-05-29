package Logicadifusa;

import java.util.ArrayList;

// Clase que representa una variable linguistica
public class VariableLinguistica {
    protected String nombre;
    protected ArrayList<ValorLinguistico> valores;
    protected double valorMin;
    protected double valorMax;
    
    // Constructor
    public VariableLinguistica(String _nombre , double _min, double _max) {
        nombre = _nombre ;
        valorMin = _min;
        valorMax = _max;
        valores = new ArrayList();
    }
    
    public void AgregarValorLinguistico(ValorLinguistico vl) {
        valores.add(vl);
    }
    
    public void AgregarValorLinguistico(String nomnre, ConjuntoDifuso conjunto) {
        valores.add(new ValorLinguistico(nombre, conjunto));
    }
    
    public void EliminarValores() {
        valores.clear();
    }
    
    ValorLinguistico ValorLinguisticoParaNombre(String nombre) {
        for(ValorLinguistico vl : valores) {
            if (vl.nombre.equalsIgnoreCase(nombre)) {
                return vl;
            }
        }
        return null;
    }
}
