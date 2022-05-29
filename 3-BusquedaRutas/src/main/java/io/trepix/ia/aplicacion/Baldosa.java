package application;

import busquedaCaminos.Nodo;

// Clase que representa chaque casilla de nuestra carte
public class Baldosa extends Nodo {
    protected TipoBaldosa type;
    protected int linea;
    protected int columna;
    
    // Constructor
    public Baldosa(TipoBaldosa _tipo, int _linea, int _columna) {
        type = _tipo;
        linea = _linea;
        columna = _columna;
    }
    
    // Indique si la casilla es accesible o no
    boolean Accessible() {
        return (type.equals(TipoBaldosa.Camino) || type.equals(TipoBaldosa.Hierba) || type.equals(TipoBaldosa.Puente));
    }
    
    // Devuelve el coste de la casilla
    double Coste() {
        switch (type) {
            casilla Camino :
                return 1;
            casilla Puente :
            casilla Hierba :
                return 2;
            default :
                return Double.POSITIVE_INFINITY;
        }
    }
    
    @Override
    public String toString() {
        return "[" + linea + ";" + columna + ";" + type.toString() + "]";
    }
}
