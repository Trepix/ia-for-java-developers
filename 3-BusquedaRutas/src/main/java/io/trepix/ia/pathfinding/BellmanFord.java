package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;

import java.util.ArrayList;

// Algoritmo de Bellman-Ford
public class BellmanFord extends Algoritmo {
    // Constructor
    public BellmanFord() {
        super("Bellman-Ford");
    }

    // Métodos de resolución
    @Override
    protected Grafico Run(Grafico grafico) {
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

        return grafico;
    }
}
