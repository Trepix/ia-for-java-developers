package io.trepix.ia.busquedaCaminos;

// Clase que representa los nodos en un gráfico
public abstract class Nodo {
    public Nodo predecesor = null;
    public double distanciaDesdeInicio = Double.POSITIVE_INFINITY;
    public double distanciaEstimada;   
}
