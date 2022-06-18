package io.trepix.ia.busquedaCaminos;

import io.trepix.ia.busquedaCaminos.structure.Arc;

import java.util.ArrayList;

// Algoritmo de Bellman-Ford
public class BellmanFord extends Algoritmo {
    // Constructor
    public BellmanFord(Grafico _grafico, IHM _ihm) {
        super("Bellman-Ford", _grafico, _ihm);
    }

    // Métodos de resolución
    @Override
    protected void Run() {
        // Iinicialización
        boolean distanciaCambiada = true;
        int i = 0;
        ArrayList<Arc> listaArcos = grafico.ListaArcos();
        
        // Bucle principal
        int numBucleMax = grafico.NumeroNodos() - 1;
        while (i < numBucleMax && distanciaCambiada) {
            distanciaCambiada = false;
            for (Arc arc : listaArcos) {
                if (arc.origin().getDistanceFromBeginning() + arc.cost() < arc.destination().getDistanceFromBeginning()) {
                    // Encontrado un camino más corto
                    arc.destination().setDistanceFromBeginning(arc.origin().getDistanceFromBeginning() + arc.cost());
                    arc.destination().setParent(arc.origin());
                    distanciaCambiada = true;
                }
            }
            i++;
        }
        
        // Prueba si el bucle es negativo
        for (Arc arc : listaArcos) {
            if (arc.origin().getDistanceFromBeginning() + arc.cost() < arc.destination().getDistanceFromBeginning()) {
                System.err.println("Bucle negativo - sin camino más corto");
            }
        }
    }
}
