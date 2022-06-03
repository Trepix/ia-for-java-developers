package io.trepix.ia.Logicadifusa;

// Clase que gestiona los puntos de las funciones de pertenencia
public class Punto2D implements Comparable {

    // Coordenadas
    public double x;
    public double y;
    
    // Constructor
    public Punto2D(double _x, double _y) {
        x = _x;
        y = _y;
    }

    // Comparador
    @Override
    public int compareTo(Object t) {
        return (int) (x - ((Punto2D) t).x);
    }
    
    // Visualización
    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
